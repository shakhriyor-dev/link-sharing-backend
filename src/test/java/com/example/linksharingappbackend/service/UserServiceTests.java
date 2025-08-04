package com.example.linksharingappbackend.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.linksharingappbackend.entity.Role;
import com.example.linksharingappbackend.entity.UserInfo;
import com.example.linksharingappbackend.repository.UserInfoRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void UserService_AddUser_ReturnsUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("abc@mail.com");
        userInfo.setUsername("abc");
        userInfo.setPassword("password");
        userInfo.setEnabled(true);
        userInfo.setRole(Role.USER);

        given(this.passwordEncoder.encode(userInfo.getPassword())).willReturn("Encoded Password");

        given(userInfoRepository.save(userInfo)).willReturn(userInfo);

        UserInfo savedUser = this.userService.addUser(userInfo);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("abc@mail.com");
        verify(this.userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    public void UserService_FindByEmail_ReturnsUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(232);
        userInfo.setEmail("aaa@mail.com");
        userInfo.setUsername("aaa");
        userInfo.setPassword("password");
        userInfo.setEnabled(true);
        userInfo.setRole(Role.USER);

        given(userInfoRepository.findByEmail("aaa@mail.com")).willReturn(Optional.of(userInfo));

        UserInfo savedUserInfo = userService.findByEmail(userInfo.getEmail());

        Assertions.assertThat(savedUserInfo).isNotNull();
        Assertions.assertThat(savedUserInfo.getEmail()).isEqualTo("aaa@mail.com");
        verify(this.userInfoRepository, times(1)).findByEmail("aaa@mail.com");
    }

    @Test
    public void UserService_FindByUsernameAndId_ReturnsUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(232);
        userInfo.setEmail("aaa@mail.com");
        userInfo.setUsername("aaa");
        userInfo.setPassword("password");
        userInfo.setEnabled(true);
        userInfo.setRole(Role.USER);

        given(userInfoRepository.findByUsernameAndId("aaa", 232)).willReturn(Optional.of(userInfo));

        UserInfo savedUserInfo = userService.findByUsernameAndId(userInfo.getUsername(), userInfo.getId());

        Assertions.assertThat(savedUserInfo).isNotNull();
        Assertions.assertThat(savedUserInfo.getUsername()).isEqualTo("aaa");
        verify(this.userInfoRepository, times(1)).findByUsernameAndId("aaa", 232);
    }

}
