package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_shopping_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingList extends BaseEntity{

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 500, nullable = false)
    private String description;
    @Column(nullable = false)
    private String image;
    private LocalDateTime closesIn;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private BigDecimal estimatedPrice;
    private LocalDateTime completedAt;
    private LocalDateTime excludedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(name = "tb_list_participants",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Setter(AccessLevel.NONE)
    private Set<User> participants = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_list_administrators",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Setter(AccessLevel.NONE)
    private Set<User> administrators = new HashSet<>();

    @OneToMany(mappedBy = "shoppingList")
    @Setter(AccessLevel.NONE)
    private Set<ShoppingListSection> sections = new HashSet<>();
}