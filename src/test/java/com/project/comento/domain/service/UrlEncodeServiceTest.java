package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlEncodeServiceTest {

    @Resource
    UrlEncodeService urlEncodeService;

    @Resource
    UrlDecodeService urlDecodeService;

    @Test
    void encodeUrl_test() {
        CompletableFuture<String> result = urlEncodeService.encodeUrl("www.kakao.com");

        result.handle((isSuccess, throwable) -> {
            Assertions.assertNotNull(throwable);
            Assertions.assertNotNull(isSuccess);

            final Url url = urlDecodeService.decodeUrl(isSuccess);
            Assertions.assertEquals(url.getShortUrl(), isSuccess);
            return isSuccess;
        });

        return;
    }
}