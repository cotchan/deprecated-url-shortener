package com.project.comento.domain.controller;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.project.comento.domain.model.Url;
import com.project.comento.domain.service.UrlDecodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
@Slf4j
public class UrlRouterController {

    @Resource
    private UrlDecodeService urlDecodeService;
    
    @Get("/{url}")
    public HttpResponse routeUsingShortUrl(@Param String url) {
        final Url result = urlDecodeService.decodeUrl(url);
        if (result == null) {
            log.error("ERROR::Requested url<{}> does not exist. Redirection is fail", url);
            return HttpResponse.of(HttpStatus.NOT_FOUND);
        }

        final String originalUrl;
        if (!StringUtils.startsWithIgnoreCase(result.getOriginalUrl(), "http")) {
            originalUrl = "http://" + result.getOriginalUrl();
        } else {
            originalUrl = result.getOriginalUrl();
        }

        log.debug("SUCCESS::Redirect as url<{}>", originalUrl);
        return HttpResponse.of(ResponseHeaders.of(HttpStatus.FOUND, "Location", originalUrl));
    }
}
