package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;

import java.util.concurrent.CompletableFuture;

public interface UrlStorageService {
    Url getByOriginalUrl(String originalUrl);
    Url getByShortUrl(String shortUrl);
    CompletableFuture<Boolean> saveUrl(Url url);
}
