package com.project.comento.global.config;

import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.project.comento.domain.controller.UrlDecodeController;
import com.project.comento.domain.controller.UrlEncodeController;
import com.project.comento.domain.controller.UrlRouterController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArmeriaConfiguration {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(UrlEncodeController urlEncodeController,
                                                               UrlDecodeController urlDecodeController,
                                                               UrlRouterController urlRouterController
                                                               ) {
        return serverBuilder -> {
            serverBuilder.serviceUnder("/docs", new DocService());
            serverBuilder.annotatedService("/decode", urlDecodeController);
            serverBuilder.annotatedService("/encode", urlEncodeController);
            serverBuilder.annotatedService("/", urlRouterController);
        };
    }
}
