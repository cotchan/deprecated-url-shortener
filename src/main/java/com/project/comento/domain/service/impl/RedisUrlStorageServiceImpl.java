package com.project.comento.domain.service.impl;

import com.project.comento.domain.model.Url;
import com.project.comento.domain.service.UrlStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class RedisUrlStorageServiceImpl implements UrlStorageService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

//    @Resource
//    private static final String keyPrefix = "U:";

    @Override
    public Url getByOriginalUrl(final String originalUrl) {
        //Redis does not support this option.
        return null;
    }

    @Override
    public Url getByShortUrl(final String shortUrl) {
        final String key = shortUrl;
        Url url = null;
        try {
            url = (Url) redisTemplate.opsForValue().get(key);
            log.debug("Success to get shortUrl from redis");
        } catch (Exception e) {
            log.error("Failed to get shortUrl into redis " + e);
        }
        return url;
    }

    @Override
    public CompletableFuture<Boolean> saveUrl(final Url url) {
        final String key = url.getShortUrl();
        try {
            redisTemplate.opsForValue().set(key, url, Duration.ofDays(1));
            log.debug("Success to save Url into redis");
        } catch (Exception e) {
            log.error("Failed to save Url into redis " + e);
        }
        return CompletableFuture.completedFuture(true);
    }
}
