package ru.war.robots.giftshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.war.robots.giftshop.model.entity.Inventory;
import ru.war.robots.giftshop.model.entity.User;
import ru.war.robots.giftshop.model.entity.Wallet;
import ru.war.robots.giftshop.model.pojo.UserPojo;
import ru.war.robots.giftshop.repo.InventoryRepository;
import ru.war.robots.giftshop.repo.UserRepository;
import ru.war.robots.giftshop.repo.WalletRepository;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    UserRepository repository;

    @Autowired
    WalletRepository walletRepository;

    public UserPojo getUser(String guid) {
        User user = repository.findByGuid(guid);

        UserPojo userPojo = new UserPojo(user);
        userPojo.setInventory(getInventory(guid));
        userPojo.setWallet(getWallet(guid));

        return userPojo;
    }

    private Wallet getWallet(String userGuid) {
        return walletRepository.findByUserGuid(userGuid);
    }

    private Inventory getInventory(String userGuid) {
        return inventoryRepository.findByUserGuid(userGuid);
    }

}
