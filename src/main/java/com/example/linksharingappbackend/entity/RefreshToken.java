package com.example.linksharingappbackend.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "REFRESH_TOKEN")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    private Instant expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_info_id", referencedColumnName = "id") // nullable=false
    @Builder.Default
    private UserInfo userInfo = new UserInfo();
    // potential problem using Builder without @Builder.Default for the relationship
}