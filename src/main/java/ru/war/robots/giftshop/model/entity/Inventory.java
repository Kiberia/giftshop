package ru.war.robots.giftshop.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inventory", indexes = {
    @Index(name = "inventory_idx", columnList = "id")
})
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_guid")
    private String userGuid;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Item> item = new java.util.ArrayList<>();
}
