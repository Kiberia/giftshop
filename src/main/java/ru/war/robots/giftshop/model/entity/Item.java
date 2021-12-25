package ru.war.robots.giftshop.model.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "items", indexes = {
    @Index(name = "items_idx", columnList = "id")
})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @Column(name = "silver_price")
    private long silverPrice;

    @Column(name = "gold_price")
    private long goldPrice;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
