package com.example.linksharingappbackend.service;

import java.io.IOException;

import com.example.linksharingappbackend.dto.ProfileDto;
import com.example.linksharingappbackend.entity.Profile;

public interface ProfileService {

    public Profile saveProfile(ProfileDto profileDto) throws IOException;
}
