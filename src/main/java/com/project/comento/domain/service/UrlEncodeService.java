package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;
import com.project.comento.domain.util.UrlConverter;
import com.project.comento.domain.util.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class UrlEncodeService {
    @Resource(name = "dualUrlStorageServiceImpl")
    private UrlStorageService urlStorageService;

    /**
     * Generates a shortened Url for the requested Url.
     * @param requestedUrl
     * @return shortUrl
     */
    public CompletableFuture<String> encodeUrl(final String requestedUrl) {
        log.debug("Hello encode shortUrl" + requestedUrl);

        final Url url = urlStorageService.getByOriginalUrl(requestedUrl);

        if (url != null) {
            return CompletableFuture.completedFuture(url.getShortUrl());
        }

        final long uuid = UuidGenerator.generate();
        final String shortUrl = UrlConverter.idToShortURL(uuid);

        final CompletableFuture<Boolean> storageResultFuture = urlStorageService.saveUrl(
                Url.builder()
                .uuid(uuid)
                .shortUrl(shortUrl)
                .originalUrl(requestedUrl)
                .build());

        final CompletableFuture<String> handleResult = storageResultFuture.handle((isSuccess, throwable) -> {
            if (!isSuccess) {
                //Fail case
                log.error("Failed to encode given url<{}>", requestedUrl);
                return Strings.EMPTY;
            }

            return UrlConverter.idToShortURL(uuid);
        });

        return handleResult;
    }
}
