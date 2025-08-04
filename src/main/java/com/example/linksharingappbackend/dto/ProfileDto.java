package com.example.linksharingappbackend.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class ProfileDto {
    @NotBlank(message = "first name is required")
    private String firstname;

    @NotBlank(message = "last name is required")
    private String lastname;

    @Email
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "file is required")
    private MultipartFile file;

    @NotBlank(message = "fileType is required")
    private String fileType;
}
