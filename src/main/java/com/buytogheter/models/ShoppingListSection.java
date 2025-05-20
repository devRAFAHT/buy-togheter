package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_shopping_list_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListSection extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

}
