package com.whitbread.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import reactor.util.annotation.NonNull;

@Validated
@ConfigurationProperties(prefix = "application")
@Getter @Setter
public class ApplicationConfig {

    @NonNull
    private String hotelApi;


}
