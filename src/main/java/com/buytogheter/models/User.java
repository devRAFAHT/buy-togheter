package com.buytogheter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Table(
        name = "tb_users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_email", columnNames = "email")
        }
)
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
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;

    @ManyToMany
    @JoinTable(
            name = "tb_user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<User> friends = new HashSet<>();

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }

}
