package com.project.comento.domain.service;

import com.project.comento.domain.model.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class UrlDecodeService {

    @Resource(name = "dualUrlStorageServiceImpl")
    private UrlStorageService urlStorageService;

    public Url decodeUrl(final String requestedUrl) {
        final Url decodeUrl = urlStorageService.getByShortUrl(requestedUrl);
        return decodeUrl;
    }
}
