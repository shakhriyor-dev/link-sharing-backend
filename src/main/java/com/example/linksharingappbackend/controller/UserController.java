package com.example.linksharingappbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linksharingappbackend.dto.UserInfoDto;
import com.example.linksharingappbackend.entity.UserInfo;
import com.example.linksharingappbackend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param userInfo
     * @return ResponseEntity<UserInfoDto>
     */
    // create separate signup dto with just the 3 required fields?
    @PostMapping("/new")
    public ResponseEntity<UserInfoDto> addNewUser(@Valid @RequestBody UserInfo userInfo) {
        UserInfo savedUserInfo = userService.addUser(userInfo);

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail(savedUserInfo.getEmail());
        userInfoDto.setId(savedUserInfo.getId());
        userInfoDto.setUsername(savedUserInfo.getUsername());
        userInfoDto.setRole(savedUserInfo.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoDto);
    }

    /**
     * @param id
     * @return ResponseEntity<UserInfoDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDto> getUserById(@PathVariable Integer id) {
        UserInfo foundUser = userService.findById(id);

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(foundUser.getId());
        userInfoDto.setEmail(foundUser.getEmail());
        userInfoDto.setUsername(foundUser.getUsername());
        userInfoDto.setProfile(foundUser.getProfile());
        userInfoDto.setRole(foundUser.getRole());
        userInfoDto.setLinks(foundUser.getLinks());

        return ResponseEntity.ok(userInfoDto);
    }

    /**
     * @param email
     * @return ResponseEntity<UserInfoDto>
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserInfoDto> getUserByEmail(@PathVariable String email) {
        UserInfo foundUser = userService.findByEmail(email);

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(foundUser.getId());
        userInfoDto.setEmail(foundUser.getEmail());
        userInfoDto.setUsername(foundUser.getUsername());
        userInfoDto.setProfile(foundUser.getProfile());
        userInfoDto.setRole(foundUser.getRole());
        userInfoDto.setLinks(foundUser.getLinks());

        return ResponseEntity.ok(userInfoDto);
    }

    /**
     * @param username
     * @param userId
     * @return ResponseEntity<UserInfoDto>
     */
    @GetMapping("/username/{username}/id/{userId}")
    public ResponseEntity<UserInfoDto> getUserByUsernameAndId(@PathVariable String username,
            @PathVariable Integer userId) {
        UserInfo foundUser = userService.findByUsernameAndId(username, userId);

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(foundUser.getId());
        userInfoDto.setEmail(foundUser.getEmail());
        userInfoDto.setUsername(foundUser.getUsername());
        userInfoDto.setProfile(foundUser.getProfile());
        userInfoDto.setRole(foundUser.getRole());
        userInfoDto.setLinks(foundUser.getLinks());

        return ResponseEntity.ok(userInfoDto);
    }

}
