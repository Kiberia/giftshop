package ru.war.robots.giftshop.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.war.robots.giftshop.model.entity.Inventory;
import ru.war.robots.giftshop.model.entity.Wallet;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, String> {
    Inventory findByUserGuid(String guid);
}
