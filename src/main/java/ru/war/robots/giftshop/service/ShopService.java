package ru.war.robots.giftshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.war.robots.giftshop.config.CacheService;
import ru.war.robots.giftshop.config.InvalidDataException;
import ru.war.robots.giftshop.jooq.tables.pojos.Item;
import ru.war.robots.giftshop.jooq.tables.pojos.TUser;
import ru.war.robots.giftshop.jooq.tables.pojos.Wallet;
import ru.war.robots.giftshop.model.dto.BuyDto;
import ru.war.robots.giftshop.model.dto.GiftDto;
import ru.war.robots.giftshop.repository.InventoryRepository;
import ru.war.robots.giftshop.repository.ItemRepository;
import ru.war.robots.giftshop.repository.UserRepository;
import ru.war.robots.giftshop.repository.WalletRepository;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Service
public class ShopService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;
    
    ConcurrentHashMap<Object, Boolean> cacheMap = new ConcurrentHashMap<>();

    public void buyItem(BuyDto dto) throws InvalidDataException {
        if (Stream.of(dto.getUserGuid(), dto.getItemId()).anyMatch(Objects::isNull)) {
            throw new InvalidDataException("Invalid data");
        }

        TUser user = userRepository.getUser(dto.getUserGuid());
        if (!userRepository.existsUser(dto.getUserGuid())) {
            throw new InvalidDataException("User not found");
        }
        Item itemToBuy = itemRepository.getItem(dto.getItemId());
        if (itemToBuy == null) {
            throw new InvalidDataException("Wrong item id");
        }

        Wallet wallet = walletRepository.getByUserGuid(dto.getUserGuid());
        if (wallet == null
            || wallet.getGoldCoins() < itemToBuy.getGoldPrice()
            || wallet.getSilverCoins() < itemToBuy.getSilverPrice()
        ) {
            throw new InvalidDataException("User can't afford this item");
        }
    
        if (CacheService.get(dto) != null)  {
            throw new InvalidDataException("Object already in transaction. Please wait for it to finish");
        }
        CacheService.put(dto);
    
        try {
            inventoryRepository.addItem(user.getGuid(), itemToBuy.getId());
    
            wallet.setSilverCoins(wallet.getSilverCoins() - itemToBuy.getSilverPrice());
            wallet.setGoldCoins(wallet.getGoldCoins() - itemToBuy.getGoldPrice());
            walletRepository.update(wallet);
        } finally {
            CacheService.remove(dto);
        }
    }

    public void gift(GiftDto dto) {
        if (Stream.of(
                dto.getSenderUserGuid(), dto.getAcceptorUserGuid(),
                dto.getItemId()
            ).anyMatch(Objects::isNull)
        ) {
            throw new InvalidDataException("Invalid data");
        }

        if (dto.getSenderUserGuid().equals(dto.getAcceptorUserGuid())) {
            throw new InvalidDataException("Can't gift to self");
        }

        if (!userRepository.existsUser(dto.getSenderUserGuid())) {
            throw new InvalidDataException("Sender not found");
        }
        if (!userRepository.existsUser(dto.getAcceptorUserGuid())) {
            throw new InvalidDataException("Acceptor not found");
        }
        if (!itemRepository.existsItem(dto.getItemId())) {
            throw new InvalidDataException("Wrong item id");
        }

        if (inventoryRepository.existsItemInInventory(dto.getSenderUserGuid(), dto.getItemId())) {
            if (CacheService.get(dto) != null)  {
                throw new InvalidDataException("Object already in transaction. Please wait for it to finish");
            }
            CacheService.put(dto);
            
            try {
                inventoryRepository.removeItem(dto.getSenderUserGuid(), dto.getItemId());
                inventoryRepository.addItem(dto.getAcceptorUserGuid(), dto.getItemId());
            } finally {
                CacheService.remove(dto);
            }
        } else {
            throw new InvalidDataException("Sender doesn't have this item");
        }
    }
}
