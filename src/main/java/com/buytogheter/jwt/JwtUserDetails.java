package com.buytogheter.jwt;

import com.buytogheter.models.User;
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.UUID;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public JwtUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public UUID getId(){
        return this.user.getId();
    }

    public String getRole(){
        return this.user.getRole().name();
    }

}