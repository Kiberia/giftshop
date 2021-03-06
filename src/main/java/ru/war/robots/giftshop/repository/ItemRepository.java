package ru.war.robots.giftshop.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.war.robots.giftshop.jooq.tables.pojos.Item;

import java.util.List;

import static ru.war.robots.giftshop.jooq.tables.Item.ITEM;

@Repository
public class ItemRepository {
    @Autowired
    DSLContext dslContext;

    public Item getItem(Long itemId) {
        return dslContext.selectFrom(ITEM)
            .where(ITEM.ID.eq(itemId))
            .fetchOneInto(Item.class);
    }

    public List<Item> getItemList(Integer limit, Integer offset) {
        return dslContext.selectFrom(ITEM)
            .limit(limit)
            .offset(offset)
            .fetchInto(Item.class);
    }

    public boolean existsItem(Long itemId) {
        return dslContext.fetchExists(ITEM, ITEM.ID.eq(itemId));
    }
}
