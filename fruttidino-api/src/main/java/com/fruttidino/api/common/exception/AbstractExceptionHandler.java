package com.fruttidino.api.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 리소스번들 설정
 */
public class AbstractExceptionHandler {

    @Autowired
    private ReloadableResourceBundleMessageSource resourceBundle;

    public ReloadableResourceBundleMessageSource getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ReloadableResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
