package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_categories")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Category extends BaseEntity{

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String image;

    @ManyToMany(mappedBy = "categories")
    private Set<Item> items = new HashSet<>();

}
