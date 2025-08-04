package com.example.linksharingappbackend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.linksharingappbackend.dto.ProfileDto;
import com.example.linksharingappbackend.entity.Profile;
import com.example.linksharingappbackend.repository.ProfileRepository;
import com.example.linksharingappbackend.repository.UserInfoRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Profile saveProfile(ProfileDto profileDto) throws IOException {

        Profile profile = new Profile();
        profile.setEmail(profileDto.getEmail());
        profile.setFirstname(profileDto.getFirstname());
        profile.setLastname(profileDto.getLastname());
        profile.setImg(profileDto.getFile().getBytes());
        profile.setFileType(profileDto.getFileType());

        Profile savedProfile = profileRepository.save(profile);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        userInfoRepository.findByEmail(username).ifPresent(userInfo -> {
            userInfo.setProfile(savedProfile);
            userInfoRepository.save(userInfo);
        });

        return savedProfile;
    }
}
