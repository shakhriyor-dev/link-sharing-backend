package com.example.linksharingappbackend.repository;

import java.time.Instant;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.linksharingappbackend.entity.RefreshToken; 

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RefreshTokenRepositoryTests {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    public void RefreshTokenRepository_Save_ReturnRefreshToken() {

        // UserInfo userInfo = new UserInfo();
        // userInfo.setEmail("aaa@mail.com");
        // userInfo.setUsername("aaa");
        // userInfo.setPassword("password");
        // userInfo.setEnabled(true);
        // userInfo.setRole(Role.USER);

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(null) // userInfo
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        Assertions.assertThat(savedRefreshToken).isNotNull();
        // Assertions.assertThat(savedRefreshToken.getUserInfo().getUsername()).isEqualTo("aaa");
    }

    @Test
    public void RefreshTokenRepository_FindByToken_ReturnRefreshToken() {

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(null)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        RefreshToken foundToken = refreshTokenRepository.findByToken(savedRefreshToken.getToken()).get();

        Assertions.assertThat(foundToken).isNotNull();
    }

}
