package com.jwtappauthentication.UserAuth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Document (collection = "user_details")
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    @Id
    private ObjectId Id;
    @NonNull
    @Indexed(unique = true)
    private String username;

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public @NonNull String getRoles() {
        return roles;
    }

    public void setRoles(@NonNull String roles) {
        this.roles = roles;
    }

    @NonNull
    private String password;
//    GrantedAuthority authorities = new SimpleGrantedAuthority("USER");
    @NonNull
    private String roles; // Roles stored as comma-separated values (e.g., "ROLE_USER,ROLE_ADMIN")

//    public UserDetails() {
//
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(roles.split(",")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


}
