package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;
import com.project.comento.domain.util.UrlChecker;
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

    public CompletableFuture<String> encodeUrl(final String requestedUrl) {

        if (!UrlChecker.isValidWithProtocol(requestedUrl) &&
            !UrlChecker.isValidWithoutProtocol(requestedUrl)) {
            log.error("Failed to encode given url<{}>, This URL does not follow the URL format.", requestedUrl);
            return CompletableFuture.completedFuture(Strings.EMPTY);
        }

        final Url url = urlStorageService.getByOriginalUrl(requestedUrl);

        if (url != null) {
            log.debug("SUCCESS::Requested encodeUrl already exists. url<{}>", url);
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
                log.error("Failed to encode given url<{}>", requestedUrl);
                return Strings.EMPTY;
            }

            return UrlConverter.idToShortURL(uuid);
        });

        return handleResult;
    }
}
