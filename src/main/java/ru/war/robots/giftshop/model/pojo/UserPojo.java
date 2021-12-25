package ru.war.robots.giftshop.model.pojo;

import ru.war.robots.giftshop.model.entity.Inventory;
import ru.war.robots.giftshop.model.entity.User;
import ru.war.robots.giftshop.model.entity.Wallet;

public class UserPojo extends User {
    private Inventory inventory;

    private Wallet wallet;

    public UserPojo(User user) {
        super(user.getGuid(), user.getLogin());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
