package ru.war.robots.giftshop.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.war.robots.giftshop.jooq.tables.pojos.Inventory;
import ru.war.robots.giftshop.jooq.tables.pojos.Wallet;

import java.util.List;
import java.util.Map;

import static ru.war.robots.giftshop.jooq.Tables.INVENTORY;
import static ru.war.robots.giftshop.jooq.tables.Wallet.WALLET;

@Repository
public class WalletRepository {
    @Autowired
    DSLContext dslContext;

    public Wallet getByUserGuid(String userGuid) {
        return dslContext.selectFrom(WALLET)
            .where(WALLET.USER_GUID.eq(userGuid))
            .fetchOneInto(Wallet.class);
    }

    public Map<String, Wallet> getMapByUserGuidList(List<String> userGuidList) {
        return dslContext.selectFrom(WALLET)
            .where(WALLET.USER_GUID.in(userGuidList))
            .fetchMap(WALLET.USER_GUID, Wallet.class);
    }

    public void update(Wallet wallet) {
        dslContext.update(WALLET)
            .set(WALLET.SILVER_COINS, wallet.getSilverCoins())
            .set(WALLET.GOLD_COINS, wallet.getGoldCoins())
            .where(WALLET.USER_GUID.eq(wallet.getUserGuid()))
            .execute();
    }
}
