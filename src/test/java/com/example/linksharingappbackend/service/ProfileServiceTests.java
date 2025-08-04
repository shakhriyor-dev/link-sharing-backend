package com.example.linksharingappbackend.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.example.linksharingappbackend.dto.ProfileDto;
import com.example.linksharingappbackend.entity.Profile;
import com.example.linksharingappbackend.repository.ProfileRepository;
import com.example.linksharingappbackend.repository.UserInfoRepository;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {

    @Mock
    ProfileRepository profileRepository;

    @Mock
    UserInfoRepository userInfoRepository;

    @InjectMocks
    ProfileServiceImpl profileService;

    @Test
    public void ProfileService_SaveProfile_ReturnsProfile() throws IOException {

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getName()).thenReturn("abc@mail.com");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String fileName = "test.jpg";
        String contentType = "image/jpeg";
        byte[] content = "Test file content".getBytes();
        MultipartFile mockFile = new MockMultipartFile(fileName, fileName, contentType, content);

        ProfileDto profileDto = new ProfileDto();
        profileDto.setEmail("abc@mail.com");
        profileDto.setFirstname("John");
        profileDto.setLastname("Smith");
        profileDto.setFile(mockFile);
        profileDto.setFileType(contentType);

        Profile profile = new Profile();
        profile.setEmail(profileDto.getEmail());
        profile.setFirstname(profileDto.getFirstname());
        profile.setLastname(profileDto.getLastname());
        profile.setFileType(profileDto.getFileType());
        profile.setImg(profileDto.getFile().getBytes());

        given(profileRepository.save(Mockito.any(Profile.class))).willReturn(profile);

        Profile savedProfile = this.profileService.saveProfile(profileDto);

        Assertions.assertThat(savedProfile).isNotNull();
        Assertions.assertThat(savedProfile.getFirstname()).isEqualTo("John");

        ArgumentCaptor<Profile> profileCaptor = ArgumentCaptor.forClass(Profile.class);
        verify(this.profileRepository, times(1)).save(profileCaptor.capture());

        Profile capturedProfile = profileCaptor.getValue();
        Assertions.assertThat(capturedProfile.getFirstname()).isEqualTo("John");
        Assertions.assertThat(capturedProfile.getLastname()).isEqualTo("Smith");
        Assertions.assertThat(capturedProfile.getEmail()).isEqualTo("abc@mail.com");
        Assertions.assertThat(capturedProfile.getFileType()).isEqualTo("image/jpeg");
        Assertions.assertThat(capturedProfile.getImg()).isEqualTo(mockFile.getBytes());
    }
}
