package ru.war.robots.giftshop.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.war.robots.giftshop.jooq.tables.daos.TUserDao;
import ru.war.robots.giftshop.jooq.tables.pojos.TUser;

import static ru.war.robots.giftshop.jooq.tables.TUser.T_USER;

@Repository
public class UserRepository extends TUserDao {
    @Autowired
    DSLContext dslContext;

    public TUser getUser(String userGuid) {
        return dslContext.selectFrom(T_USER)
            .where(T_USER.GUID.eq(userGuid))
            .fetchOneInto(TUser.class);
    }

    public boolean existsUser(String userGuid) {
        return dslContext.fetchExists(T_USER, T_USER.GUID.eq(userGuid));
    }
}
