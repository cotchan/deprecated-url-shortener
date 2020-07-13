package com.project.comento.domain.service.impl;

import com.project.comento.domain.model.Url;
import com.project.comento.domain.service.MySqlUrlStorageService;
import com.project.comento.domain.service.UrlStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class DualUrlStorageServiceImpl implements UrlStorageService {

    @Resource
    private MySqlUrlStorageService mySqlUrlStorageService;

    @Resource
    private RedisUrlStorageServiceImpl redisUrlStorageService;

    @Override
    public Url getByShortUrl(String shortUrl) {
        Url url = redisUrlStorageService.getByShortUrl(shortUrl);
        if (url == null) {
            url = mySqlUrlStorageService.findByShortUrl(shortUrl);
        }
        return url;
    }

    @Override
    public Url getByOriginalUrl(String originalUrl) {
        return mySqlUrlStorageService.findByOriginalUrl(originalUrl);
    }

    @Override
    public CompletableFuture<Boolean> saveUrl(Url url) {
        final CompletableFuture<Boolean> redisStorageResultFuture = redisUrlStorageService.saveUrl(url);

        redisStorageResultFuture.handle((isSuccess, throwable) -> {
            if (throwable != null) {
                log.error("Exception is thrown by redisUrlStorage " + throwable);
            }

            if (!isSuccess) {
                log.error("Failed to set url in redis");
            }

            return null;
        });

        try {
            mySqlUrlStorageService.save(url);
        } catch (Exception e) {
            log.error("Error occurred in db operation " + e);
            return CompletableFuture.completedFuture(false);
        }
        log.info("Given shortenedUrl<{}> is successfully saved at storage.", url);
        return CompletableFuture.completedFuture(true);
    }
}
