package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlDecodeServiceTest {

    @Resource
    UrlDecodeService urlDecodeService;

    @Test
    void decodeUrl_test() {
        //Success case
        final Url result = urlDecodeService.decodeUrl("jKn2ST9Yf20");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getOriginalUrl(), "www.kakao8044060538391527420.com");
    }
}