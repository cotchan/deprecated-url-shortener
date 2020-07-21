package com.project.comento.domain.controller;

import com.google.gson.Gson;
import com.linecorp.armeria.common.HttpData;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.project.comento.domain.model.Url;
import com.project.comento.domain.service.UrlDecodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class UrlDecodeController {

    @Resource
    UrlDecodeService urlDecodeService;

    @Get("/{url}")
    public HttpResponse decodeUrl(@Param String url) {
        final Url result = urlDecodeService.decodeUrl(url);
        if (result == null) {
            log.error("ERROR::Requested shortUrl<{}> does not exist", url);
            return HttpResponse.of(HttpStatus.NOT_FOUND);
        }

        log.debug("SUCCESS::decodeUrl Method Result is <{}>", result.toString());
        return HttpResponse.of(HttpStatus.OK, MediaType.JSON_UTF_8,
                HttpData.ofUtf8(new Gson().toJson(result)));
    }
}
