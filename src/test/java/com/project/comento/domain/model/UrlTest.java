package com.project.comento.domain.model;

import com.fasterxml.jackson.databind.jsontype.impl.AsExistingPropertyTypeSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

class UrlTest {
    @Test
    public void UrlGenerateTest() {
        Url url = Url.builder()
                .uuid(12345)
                .originalUrl("www.kakao.com")
                .shortUrl("1a3d")
                .build();

        Assertions.assertEquals(12345, url.getUuid());
        Assertions.assertEquals("www.kakao.com",url.getOriginalUrl());
        Assertions.assertEquals("1a3d",url.getShortUrl());
    }
}