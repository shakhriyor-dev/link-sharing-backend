package com.example.linksharingappbackend.service;

import java.util.List;

import com.example.linksharingappbackend.entity.Link;

import jakarta.persistence.EntityNotFoundException;

public interface LinkService {

    public Link saveLink(Link link);

    public List<Link> fetchLinkList();

    public Link fetchLinkById(Integer linkId) throws EntityNotFoundException;

    public void deleteLinkById(Integer linkId);
}
