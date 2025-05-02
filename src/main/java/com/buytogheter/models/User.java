package com.buytogheter.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String username;
    @Column(length = 300, nullable = false)
    private String email;
    private String password;
    private String avatar;
    private String createdAt;


}
