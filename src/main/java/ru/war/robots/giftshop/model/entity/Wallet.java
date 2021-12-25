package ru.war.robots.giftshop.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "wallet", indexes = {
    @Index(name = "wallet_idx", columnList = "user_guid")
})
public class Wallet {
    @Id
    @Column(name="user_guid")
    private String userGuid;

    @Column(name="silver_coins")
    private long silverCoins;

    @Column(name="gold_coins")
    private long goldCoins;

    public long getSilverCoins() {
        return silverCoins;
    }

    public void setSilverCoins(Long silverCoins) {
        this.silverCoins = silverCoins;
    }

    public long getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(long goldCoins) {
        this.goldCoins = goldCoins;
    }
}
