package com.project.comento.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.armeria.server.annotation.ConsumesJson;
import com.linecorp.armeria.server.annotation.Post;
import com.project.comento.domain.model.RequestUrl;
import com.project.comento.domain.service.UrlEncodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class UrlEncodeController {

    @Resource
    UrlEncodeService urlEncodeService;

    @Post("/")
    @ConsumesJson
    public CompletableFuture<String> encodeUrl(@RequestBody String requestBody) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        log.debug("RequestBody::<>", requestBody);
        final RequestUrl requestUrl = objectMapper.readValue(requestBody, RequestUrl.class);
        return urlEncodeService.encodeUrl(requestUrl.getUrl());
    }
}
