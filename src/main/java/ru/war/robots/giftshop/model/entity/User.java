package ru.war.robots.giftshop.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "users_idx", columnList = "guid")
})
public class User {
    @Id
    private String guid;

    private String login;

    public User() {}

    public User(String guid, String login) {
        this.guid = guid;
        this.login = login;
    }

    public String getGuid() {
        return guid;
    }

    public String getLogin() {
        return login;
    }
}
