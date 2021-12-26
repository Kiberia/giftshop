package ru.war.robots.giftshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.war.robots.giftshop.config.InvalidDataException;
import ru.war.robots.giftshop.jooq.tables.pojos.Inventory;
import ru.war.robots.giftshop.jooq.tables.pojos.Item;
import ru.war.robots.giftshop.jooq.tables.pojos.TUser;
import ru.war.robots.giftshop.jooq.tables.pojos.Wallet;
import ru.war.robots.giftshop.model.dto.BuyDto;
import ru.war.robots.giftshop.model.dto.GiftDto;
import ru.war.robots.giftshop.model.pojo.UserPojo;
import ru.war.robots.giftshop.repository.InventoryRepository;
import ru.war.robots.giftshop.repository.ItemRepository;
import ru.war.robots.giftshop.repository.UserRepository;
import ru.war.robots.giftshop.repository.WalletRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReferenceService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    public List<UserPojo> getUserList() {
        //берём всех пользователей (с заделом на пагинацию)
        List<TUser> users = userRepository.getAllUsers(Integer.MAX_VALUE, 0);

        List<String> selectedGuids = users.stream()
            .map(TUser::getGuid)
            .collect(Collectors.toList());

        Map<String, List<Inventory>> inventories = inventoryRepository.getMapByUserGuidList(selectedGuids);
        Map<String, Wallet> wallets = walletRepository.getMapByUserGuidList(selectedGuids);

        return users.stream()
            .map(item -> {
                UserPojo user = new UserPojo(item);

                user.setInventory(inventories.get(item.getGuid()));
                user.setWallet(wallets.get(item.getGuid()));

                return user;
            })
            .collect(Collectors.toList());
    }

    public List<Item> getItemList() {
        //берём всех пользователей (с заделом на пагинацию)
        return itemRepository.getItemList(Integer.MAX_VALUE, 0);
    }
}
