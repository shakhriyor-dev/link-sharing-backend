package com.example.linksharingappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.linksharingappbackend.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
