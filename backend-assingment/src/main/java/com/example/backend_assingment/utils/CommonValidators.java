package com.example.backend_assingment.utils;

import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.exception.ApiException;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class CommonValidators {
    private static final String NAME_PATTERN = "^[a-zA-Z]+$";
    private static final String MOBILE_PATTERN = "^[0-9]{10}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";

    public static void validateFirstName(String firstName) {
        if (StringUtils.isBlank(firstName) || !Pattern.matches(NAME_PATTERN, firstName)) {
            throw new ApiException(
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getTitle(),
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getMessage() + ": First Name",
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER);
        }
    }

    public static void validateLastName(String lastName) {
        if (StringUtils.isBlank(lastName) || !Pattern.matches(NAME_PATTERN, lastName)) {
            throw new ApiException(
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getTitle(),
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getMessage() + ": Last Name",
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER);
        }
    }

    public static void validateMobile(String mobile) {
        if (StringUtils.isBlank(mobile) || !Pattern.matches(MOBILE_PATTERN, mobile)) {
            throw new ApiException(
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getTitle(),
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getMessage() + ": Mobile number",
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER);
        }
    }

    public static void validatePassword(String password) {
        if (StringUtils.isBlank(password) ||
            password.length() < 8 || password.length() > 15 ||
            !Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new ApiException(
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getTitle(),
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getMessage() + ": Password",
                ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER);
        }
    }
}
