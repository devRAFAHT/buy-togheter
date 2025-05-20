package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_item_purchase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchase extends BaseEntity {

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(length = 250)
    private String observation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_list_item_id", nullable = false)
    private ShoppingListItem shoppingListItem;
    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = false)
    private Establishment establishment;
}
