package ru.war.robots.giftshop.repository;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.war.robots.giftshop.jooq.tables.pojos.Inventory;

import java.util.List;
import java.util.Map;

import static ru.war.robots.giftshop.jooq.Tables.INVENTORY;

@Repository
public class InventoryRepository {
    @Autowired
    DSLContext dslContext;

    public List<Inventory> getInventoryByUserGuid(String userGuid) {
        return dslContext.selectFrom(INVENTORY)
            .where(INVENTORY.USER_GUID.eq(userGuid))
            .fetchInto(Inventory.class);
    }

    public Map<String, List<Inventory>> getMapByUserGuidList(List<String> userGuidList) {
        return dslContext.selectFrom(INVENTORY)
            .where(INVENTORY.USER_GUID.in(userGuidList))
            .fetchGroups(INVENTORY.USER_GUID, Inventory.class);
    }

    public void addItem(String userGuid, Long itemId) {
        if (existsRecordInInventory(userGuid, itemId)) {
            dslContext.update(INVENTORY)
                .set(INVENTORY.QUANTITY, INVENTORY.QUANTITY.plus(1L))
                .where(
                    INVENTORY.USER_GUID.eq(userGuid),
                    INVENTORY.ITEM_ID.eq(itemId)
                )
                .execute();
        } else {
            dslContext.insertInto(INVENTORY)
                .columns(INVENTORY.USER_GUID, INVENTORY.ITEM_ID, INVENTORY.QUANTITY)
                .values(userGuid, itemId, 1L)
                .execute();
        }
    }

    public boolean existsRecordInInventory(String userGuid, Long itemId) {
        Condition condition = DSL.and(
            INVENTORY.USER_GUID.eq(userGuid),
            INVENTORY.ITEM_ID.eq(itemId)
        );

        return dslContext.fetchExists(INVENTORY, condition);
    }

    public boolean existsItemInInventory(String userGuid, Long itemId) {
        Condition condition = DSL.and(
            INVENTORY.USER_GUID.eq(userGuid),
            INVENTORY.ITEM_ID.eq(itemId),
            INVENTORY.QUANTITY.gt(0L)
        );

        return dslContext.fetchExists(INVENTORY, condition);
    }

    public void removeItem(String userGuid, Long itemId) {
        dslContext.update(INVENTORY)
            .set(INVENTORY.QUANTITY, INVENTORY.QUANTITY.minus(1))
            .where(
                INVENTORY.USER_GUID.eq(userGuid),
                INVENTORY.ITEM_ID.eq(itemId)
            )
            .execute();
    }
    
    public void wipeItem(String userGuid, Long itemId) {
        dslContext.deleteFrom(INVENTORY)
            .where(
                INVENTORY.USER_GUID.eq(userGuid),
                INVENTORY.ITEM_ID.eq(itemId)
            )
            .execute();
    }
}
