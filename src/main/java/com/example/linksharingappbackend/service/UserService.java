package com.example.linksharingappbackend.service;

import com.example.linksharingappbackend.entity.UserInfo;

public interface UserService {

    public UserInfo addUser(UserInfo userInfo);

    public UserInfo findById(Integer id);

    public UserInfo findByEmail(String email);

    public UserInfo findByUsernameAndId(String username, Integer id);
}
