package ru.war.robots.giftshop.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.war.robots.giftshop.model.entity.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, String> {

}
