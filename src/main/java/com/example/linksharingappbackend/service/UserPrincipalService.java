package com.example.linksharingappbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.linksharingappbackend.dto.UserPrincipal;
import com.example.linksharingappbackend.entity.UserInfo;
import com.example.linksharingappbackend.repository.UserInfoRepository;

@Component
public class UserPrincipalService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserPrincipal loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByEmail(name);
        return userInfo.map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("user " + name + " not found"));

    }
}
