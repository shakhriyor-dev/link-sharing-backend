package com.example.linksharingappbackend.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.linksharingappbackend.validation.ValidateUniqueEmailType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_INFO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "username is required")
    private String username;

    @Column(unique = true)
    @Email
    @NotBlank(message = "email is required")
    @ValidateUniqueEmailType
    private String email;

    @NotBlank(message = "password is required")
    private String password;
    private Boolean enabled;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant lastUpdatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROFILE_ID", referencedColumnName = "id")
    private Profile profile;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Link> links = new ArrayList<>();

    // could add refreshToken mapping here as well
}