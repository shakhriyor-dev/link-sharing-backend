package com.example.linksharingappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.linksharingappbackend.entity.Role;
import com.example.linksharingappbackend.entity.UserInfo;
import com.example.linksharingappbackend.exception.UserNotFoundException;
import com.example.linksharingappbackend.repository.UserInfoRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRole(Role.USER);
        userInfo.setEnabled(true);
        return this.userInfoRepository.save(userInfo);
    }

    public UserInfo findById(Integer id) {
        return this.userInfoRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserInfo findByEmail(String email) {
        return this.userInfoRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserInfo findByUsernameAndId(String username, Integer id) {
        return this.userInfoRepository.findByUsernameAndId(username, id)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username + " and id: " + id));
    }
}
