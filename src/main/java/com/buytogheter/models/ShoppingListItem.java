package com.buytogheter.models;

import com.buytogheter.enums.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Entity
@Table(name = "tb_shopping_list_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    @Column(nullable = false)
    private Integer quantityNeeded;
    @Column(nullable = false)
    private Integer quantityPurchased;
    @Column(nullable = false)
    private BigDecimal estimatedPrice;
    @Column(nullable = false)
    private BigDecimal pricePurchased;
    @Column(length = 500)
    private String observation;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "section_id")
    private ShoppingListSection section;

    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private User responsible;
}
