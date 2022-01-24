package com.fruttidino.api.common.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 리소스번들 적용
 */
@Configuration
public class ErrorMessageSourceConfig {

    @Bean
    public ReloadableResourceBundleMessageSource errorMessgeSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:/i18n/messages");
        reloadableResourceBundleMessageSource.setCacheSeconds(60);
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return reloadableResourceBundleMessageSource;
    }
}
