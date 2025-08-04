package com.example.linksharingappbackend.dto;

import java.util.List;

import com.example.linksharingappbackend.entity.Link;
import com.example.linksharingappbackend.entity.Profile;
import com.example.linksharingappbackend.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    Integer id;
    String email;
    String username;
    Role role;
    Profile profile;
    List<Link> links;
    // could add enabled as well
}
