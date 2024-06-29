package com.example.backend_assingment.dto.processors;

import com.example.backend_assingment.configs.CoinApiconfig;
import com.example.backend_assingment.dto.Entities.EApiLog;
import com.example.backend_assingment.dto.Entities.ApiRequestType;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.dto.repository.IApiLogRepository;
import com.example.backend_assingment.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ApiProcessor {

    private IApiLogRepository requestResponseRepository;
    private CoinApiconfig coinApiConfig;

    public ApiProcessorResponse processData(ApiRequest request, ApiRequestType requestType) {

        EApiLog requsetLog = new EApiLog();
        requsetLog.setUserId(request.getUserId());
        requsetLog.setRequestUrl(coinApiConfig.getThirdPartyApiUrl());
        requsetLog.setApiType(requestType.name());
        requsetLog.setResponsePayload(
            JsonUtils.getJson(request)
        );

        ResponseEntity<String> response;

        try {
            switch (requestType){
                case COIN_VIEW:
                    response = executeRequest(
                        coinApiConfig.getThirdPartyApiUrl(), request, getHeaders(coinApiConfig.getApiKey()));
                    break;
                default:
                    return null;
            }
        }catch (Exception e) {
            log.error("Error while calling External Api, Exception ", e);
            requsetLog.setResponsePayload(
                JsonUtils.getJson(e)
            );
            throw new ApiException(
               ApiResponseCodes.SERVER_ERROR.getTitle(),
                ApiResponseCodes.SERVER_ERROR.getMessage(),
                ApiResponseCodes.SERVER_ERROR
            );
        }


        requsetLog.setResponsePayload(response.getBody());

        requestResponseRepository.save(requsetLog);

        return new ApiProcessorResponse(
            (HttpStatus) response.getStatusCode(),
            response.getBody()
        );

    }

    private ResponseEntity<String> executeRequest(String thirdPartyApiUrl, ApiRequest request, HttpHeaders headers) {
        RestTemplate restTemplate= new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(thirdPartyApiUrl)
            .queryParam("symbol", request.getRequestBody());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            String.class
        );

    }

    private HttpHeaders getHeaders(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        return headers;
    }

}
