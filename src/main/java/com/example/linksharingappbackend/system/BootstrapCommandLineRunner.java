package com.example.linksharingappbackend.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.linksharingappbackend.entity.Role;
import com.example.linksharingappbackend.entity.UserInfo;
import com.example.linksharingappbackend.repository.UserInfoRepository;

@Component
public class BootstrapCommandLineRunner implements CommandLineRunner {

    private final UserInfoRepository userInfoRepository;

    public BootstrapCommandLineRunner(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        UserInfo adminUser = new UserInfo();
        adminUser.setEmail("admin@admin.com");
        adminUser.setEnabled(true);
        adminUser.setRole(Role.ADMIN);
        adminUser.setPassword("admin");
        adminUser.setUsername("admin");

        this.userInfoRepository.save(adminUser);
    }

}
