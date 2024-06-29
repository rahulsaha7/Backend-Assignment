package com.example.backend_assingment.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class BasicConfiguration {
    @Value("${predefined.auth.key}")
    private String predefinedAuthKey;

    @Value("${predefined.origin}")
    private String predefinedOrigin;
}
