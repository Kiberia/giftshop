package ru.war.robots.giftshop.model.pojo;

import ru.war.robots.giftshop.jooq.tables.pojos.Inventory;
import ru.war.robots.giftshop.jooq.tables.pojos.TUser;
import ru.war.robots.giftshop.jooq.tables.pojos.Wallet;

import java.util.List;

public class UserPojo extends TUser {
    private List<Inventory> inventory;

    private Wallet wallet;
    
    public UserPojo() {
    }
    
    public UserPojo(TUser user) {
        super(user.getGuid(), user.getLogin());
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
