package com.example.backend_assingment.coins_view.service;

import com.example.backend_assingment.coins_view.model.CoinViewApiResponse;
import com.example.backend_assingment.dto.Entities.ApiRequestType;
import com.example.backend_assingment.dto.Entities.ApiResponse;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.dto.processors.ApiProcessor;
import com.example.backend_assingment.dto.processors.ApiProcessorResponse;
import com.example.backend_assingment.dto.processors.ApiRequest;
import com.example.backend_assingment.dto.repository.IUserRepository;
import com.example.backend_assingment.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CoinService implements ICoinService{

    private ApiProcessor apiProcessor;
    private IUserRepository userRepository;

    @Override
    public CoinViewApiResponse searchCoinsByName(String searchSymbol, String userName) {
        return userRepository.findByUserName(userName)
            .map(user -> processApiRequest(user.getId(), searchSymbol))
            .orElse(new CoinViewApiResponse());
    }

    private CoinViewApiResponse processApiRequest(Long id, String searchSymbol){
        ApiProcessorResponse response = apiProcessor.processData(new ApiRequest(id, searchSymbol), ApiRequestType.COIN_VIEW);
        CoinViewApiResponse coinViewResponse = null;
        return JsonUtils.getJsonObject(response.getData().toString(),
                CoinViewApiResponse.class);
    }

}

