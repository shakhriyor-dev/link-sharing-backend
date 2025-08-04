package com.example.linksharingappbackend.entity;

import com.example.linksharingappbackend.validation.ValidatePlatformMatch;
import com.example.linksharingappbackend.validation.ValidatePlatformType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PLATFORM_LINK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValidatePlatformMatch
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ValidatePlatformType
    private String platform;

    @NotBlank
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    @JsonBackReference
    private UserInfo userInfo;
}
