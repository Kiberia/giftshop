package ru.war.robots.giftshop.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.war.robots.giftshop.model.entity.Item;
import ru.war.robots.giftshop.model.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByGuid(String guid);
}
