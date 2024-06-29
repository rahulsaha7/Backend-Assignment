package com.example.backend_assingment.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CoinApiconfig {

    @Value("${thirdparty.coin.api.url}")
    private String thirdPartyApiUrl;

    @Value("${thirdparty.coin.api.key}")
    private String apiKey;

}
