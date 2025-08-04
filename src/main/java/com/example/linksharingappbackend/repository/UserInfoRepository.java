package com.example.linksharingappbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.linksharingappbackend.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findByUsernameAndId(String username, int id);
}
