package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User extends BaseEntity{

    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String username;
    @Column(length = 300, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "tb_user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<User> friends = new HashSet<>();

}
