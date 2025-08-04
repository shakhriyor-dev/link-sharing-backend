package com.example.linksharingappbackend.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.linksharingappbackend.entity.UserInfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private UserInfo userInfo;

    public UserInfo getUser() {
        return userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userInfo.getRole().name()));
    }

    @Override
    public String getPassword() {
        return this.userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userInfo.getEnabled();
    }

}
