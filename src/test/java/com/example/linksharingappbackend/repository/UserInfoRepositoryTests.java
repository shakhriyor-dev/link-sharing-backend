package com.example.linksharingappbackend.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.linksharingappbackend.entity.Role;
import com.example.linksharingappbackend.entity.UserInfo;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserInfoRepositoryTests {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void UserInfoRepository_Save_ReturnSavedUserInfo() {

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("aaa@mail.com");
        userInfo.setUsername("aaa");
        userInfo.setPassword("password");
        userInfo.setEnabled(true);
        userInfo.setRole(Role.USER);

        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        Assertions.assertThat(savedUserInfo).isNotNull();
        Assertions.assertThat(savedUserInfo.getId()).isGreaterThan(0);
    }

    @Test
    public void UserInfoRepository_FindByEmail_ReturnSavedUserInfo() {

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("aaa@mail.com");
        userInfo.setUsername("aaa");
        userInfo.setPassword("password");
        userInfo.setEnabled(true);
        userInfo.setRole(Role.USER);

        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        UserInfo foundUserInfo = userInfoRepository.findByEmail(savedUserInfo.getEmail()).get();

        Assertions.assertThat(foundUserInfo).isNotNull();
        Assertions.assertThat(foundUserInfo.getEmail()).isEqualTo("aaa@mail.com");
    }

    @Test
    public void UserInfoRepository_FindByUsernameAndId_ReturnSavedUserInfo() {

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("green@mail.com");
        userInfo.setUsername("green");
        userInfo.setPassword("yellow");
        userInfo.setEnabled(true);
        userInfo.setRole(Role.USER);

        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        UserInfo foundUserInfo = userInfoRepository
                .findByUsernameAndId(savedUserInfo.getUsername(), savedUserInfo.getId()).get();

        Assertions.assertThat(foundUserInfo).isNotNull();
        Assertions.assertThat(foundUserInfo.getUsername()).isEqualTo("green");
    }

}
