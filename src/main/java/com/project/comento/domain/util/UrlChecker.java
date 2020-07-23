package com.project.comento.domain.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.validator.routines.UrlValidator;

@Component
@Slf4j
public final class UrlChecker {

    private static final String[] SCHEMES = {"https", "http"};

    public static boolean isValidWithProtocol(String url) {
        try {
            UrlValidator defaultValidator = new UrlValidator(SCHEMES);
            return defaultValidator.isValid(url);
        } catch (NullPointerException e)
        {
            log.error("NullPointer Error occurred in isValidWithProtocol operation " + e);
            throw new NullPointerException();
        }
    }

    public static boolean isValidWithoutProtocol(String url) {
        try {
            UrlValidator defaultValidator = new UrlValidator();
            for (int loop = 0; loop < SCHEMES.length; ++loop)
            {
                String newUrl = SCHEMES[loop] + "://" + url;
                if(defaultValidator.isValid(newUrl)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e)
        {
            log.error("NullPointer Error occurred in isValidWithoutProtocol operation " + e);
            throw new NullPointerException();
        }
    }
}
