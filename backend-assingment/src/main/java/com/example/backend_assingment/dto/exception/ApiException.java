package com.example.backend_assingment.dto.exception;

import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.ResponseCodeType;
import lombok.Getter;


@Getter
public class ApiException extends RuntimeException{

    private final String title;
    private final String message;
    private final ApiResponseCodes errorCode;

    public ApiException(String title, String message, ApiResponseCodes errorCode) {
        super(message);
        this.title = title;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ApiException(String title, String message, ApiResponseCodes responseCodeType, Throwable cause) {
        super(message, cause);
        this.title = title;
        this.message = message;
        this.errorCode = responseCodeType;
    }



}
