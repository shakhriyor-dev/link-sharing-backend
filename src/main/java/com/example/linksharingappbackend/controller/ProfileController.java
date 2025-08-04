package com.example.linksharingappbackend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linksharingappbackend.dto.ProfileDto;
import com.example.linksharingappbackend.entity.Profile;
import com.example.linksharingappbackend.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * @param profileDto
     * @return ResponseEntity<Profile>
     * @throws IOException
     */
    @PostMapping()
    public ResponseEntity<Profile> saveProfile(@ModelAttribute ProfileDto profileDto) throws IOException {
        Profile savedProfile = profileService.saveProfile(profileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

}
