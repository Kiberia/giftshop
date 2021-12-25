package ru.war.robots.giftshop.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.war.robots.giftshop.model.entity.User;
import ru.war.robots.giftshop.model.entity.Wallet;

public interface WalletRepository extends PagingAndSortingRepository<Wallet, String> {
    Wallet findByUserGuid(String guid);
}
