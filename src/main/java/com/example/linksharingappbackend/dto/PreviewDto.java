package com.example.linksharingappbackend.dto;

import java.util.List;

import com.example.linksharingappbackend.entity.Link;
import com.example.linksharingappbackend.entity.Profile;

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
public class PreviewDto {
    private Profile profile;
    private List<Link> links;
}
