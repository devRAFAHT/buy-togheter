package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal averagePrice;
    @Column(nullable = false)
    private Integer usageCount;

    @ManyToMany()
    @JoinTable(
            name = "tb_item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();


}
