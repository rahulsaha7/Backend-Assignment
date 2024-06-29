package com.example.backend_assingment.coins_view.controller;

import com.example.backend_assingment.coins_view.model.CoinViewApiResponse;
import com.example.backend_assingment.coins_view.model.CoinViewRequest;
import com.example.backend_assingment.coins_view.service.ICoinService;
import com.example.backend_assingment.dto.AuthPermissions;
import com.example.backend_assingment.dto.Entities.ApiResponse;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.AuthModule;
import com.example.backend_assingment.dto.annotaions.Authenticate;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.utils.ErrorUtils;
import com.example.backend_assingment.utils.UtilConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jdk.jshell.execution.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/backend_assignment/coins-view")
public class ViewCoinsController {

    @Autowired
    private ICoinService coinService;

    @GetMapping("/get-coins-data")
    @Authenticate(module = AuthModule.AUTH_USER, permissions = {AuthPermissions.AUTHENTICATED})
    public ResponseEntity<ApiResponse<CoinViewApiResponse>> searchCoinsData(
        HttpServletRequest request,
        @Valid CoinViewRequest coinViewRequest, BindingResult result) {

        if (result.hasErrors()) {
            throw new ApiException(
                ApiResponseCodes.COIN_VIEW_INVALID_PARAMS.getTitle(),
                ApiResponseCodes.COIN_VIEW_INVALID_PARAMS.getMessage() + ErrorUtils.getBeanErrors(result),
                ApiResponseCodes.COIN_VIEW_INVALID_PARAMS);
        }

        return new ResponseEntity<>(
            new ApiResponse<>(true, ApiResponseCodes.COIN_VIEW_SUCCESS.getCode(),
                ApiResponseCodes.COIN_VIEW_SUCCESS.getMessage(),
                coinService.searchCoinsByName(coinViewRequest.getSymbol(), request.getAttribute(UtilConstants.AUTH_USER_ATTRIBUTE).toString())),
            HttpStatus.OK);

    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleException(ApiException e) {
        log.error("Something went wrong. Exception : {}", e);
        return new ResponseEntity<>(
            new ApiResponse<>(false, e.getErrorCode().getCode(),
                e.getMessage(),
                null),
            HttpStatus.OK);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("Something went wrong while . Exception : {}", e);
        return new ResponseEntity<>(
            new ApiResponse<>(false, ApiResponseCodes.SERVER_ERROR.getCode(),
                ApiResponseCodes.SERVER_ERROR.getMessage(),
                null),
            HttpStatus.OK);
    }

}
