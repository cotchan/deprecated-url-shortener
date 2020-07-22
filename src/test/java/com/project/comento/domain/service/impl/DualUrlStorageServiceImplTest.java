package com.project.comento.domain.service.impl;

import com.project.comento.domain.model.Url;
import com.project.comento.domain.service.MySqlUrlStorageService;
import com.project.comento.domain.util.UrlConverter;
import com.project.comento.domain.util.UuidGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;


@SpringBootTest
class DualUrlStorageServiceImplTest {

    @Resource
    DualUrlStorageServiceImpl dualUrlStorageService;

    @Resource
    MySqlUrlStorageService mySqlUrlStorageService;

    @Resource
    RedisUrlStorageServiceImpl redisUrlStorageService;

    @Test
    void dualUrlStorageService_getByShortUrl_Test() {
        final String requestUrl = "i2BrwSQylsT";
        final Url result = dualUrlStorageService.getByShortUrl(requestUrl);

        //success case
        Assertions.assertEquals(result.getUuid(),7451353941285448213L);
        Assertions.assertEquals(result.getOriginalUrl(),"www.kakao7451353941285448213.com");
        Assertions.assertEquals(result.getShortUrl(), requestUrl);
    }

    @Test
    void dualUrlStorageService_getByOriginalUrl_Test() {
        final String requestUrl = "www.kakao6834888262713107958.com";
        final Url result = dualUrlStorageService.getByOriginalUrl(requestUrl);

        //success case
        Assertions.assertEquals(result.getUuid(),6834888262713107958L);
        Assertions.assertEquals(result.getShortUrl(), "ii31wUshlBy");
        Assertions.assertEquals(result.getOriginalUrl(),requestUrl);
    }

    @Test
    void dualUrlStorageService_saveTest() {
        final long uuid = UuidGenerator.generate();
        final String shortUrl = UrlConverter.idToShortURL(uuid);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("www.");
        stringBuilder.append("kakao");
        stringBuilder.append(uuid);
        stringBuilder.append(".com");
        final String originalUrl = stringBuilder.toString();

        Url url = Url.builder()
                .uuid(uuid)
                .shortUrl(shortUrl)
                .originalUrl(originalUrl)
                .build();

        dualUrlStorageService.saveUrl(url);
    }

    @Test
    void mySqlService_saveTest() {
        for (int loop = 0; loop < 10; ++loop) {
            final long uuid = UuidGenerator.generate();
            final String shortUrl = UrlConverter.idToShortURL(uuid);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("www.");
            stringBuilder.append("kakao");
            stringBuilder.append(uuid);
            stringBuilder.append(".com");
            final String originalUrl = stringBuilder.toString();

            Url url = Url.builder()
                    .uuid(uuid)
                    .shortUrl(shortUrl)
                    .originalUrl(originalUrl)
                    .build();

            mySqlUrlStorageService.save(url);
        }
    }

    @Test
    void mySqlService_findTest() {
        final String requestUrl = "www.kakao5913616464117977887.com";
        final Url result = mySqlUrlStorageService.findByOriginalUrl(requestUrl);

        Assertions.assertEquals(result.getShortUrl(), "hc0AAglV7YF");
        Assertions.assertEquals(result.getUuid(), 5913616464117977887L);

        final Url result2 = mySqlUrlStorageService.findByShortUrl(result.getShortUrl());
        Assertions.assertEquals(result2.getOriginalUrl(), requestUrl);
        Assertions.assertEquals(result2.getUuid(), result.getUuid());
    }

    @Test
    void redisService_saveTest() {
        for (int loop = 0; loop < 10; ++loop) {
            final long uuid = UuidGenerator.generate();
            final String shortUrl = UrlConverter.idToShortURL(uuid);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("www.");
            stringBuilder.append("kakao");
            stringBuilder.append(uuid);
            stringBuilder.append(".com");
            final String originalUrl = stringBuilder.toString();

            Url url = Url.builder()
                    .uuid(uuid)
                    .shortUrl(shortUrl)
                    .originalUrl(originalUrl)
                    .build();

            CompletableFuture<Boolean> result = redisUrlStorageService.saveUrl(url);
            Assertions.assertEquals(result.join(), true);
        }
    }

    @Test
    void redisService_findTest() {
        final String requestUrl = "k4mIAUMhsQp";
        final Url url = redisUrlStorageService.getByShortUrl(requestUrl);

        Assertions.assertEquals(url.getUuid(),9153811840949267307L);
        Assertions.assertEquals(url.getOriginalUrl(),"www.kakao9153811840949267307.com");
    }
}