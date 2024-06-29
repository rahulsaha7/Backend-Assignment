package com.example.backend_assingment.auth_module.cotroller;

import com.example.backend_assingment.auth_module.model.LoginRequest;
import com.example.backend_assingment.auth_module.model.SignUpRequest;
import com.example.backend_assingment.auth_module.service.IAuthService;
import com.example.backend_assingment.dto.AuthPermissions;
import com.example.backend_assingment.dto.Entities.ApiResponse;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.AuthModule;
import com.example.backend_assingment.dto.annotaions.AuthUpgrade;
import com.example.backend_assingment.dto.annotaions.Authenticate;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.utils.ErrorUtils;
import com.example.backend_assingment.utils.UtilConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/backend_assignment/auth")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private IAuthService authService;

    @GetMapping("/change-username")
    public String setCookie(HttpServletResponse response) {
        // create a cookie
        Cookie cookie = new Cookie("username", "Jovan");

        //add cookie to response
        response.addCookie(cookie);

        return "Username is changed!";
    }

    @PostMapping("/sign-up")
    @Authenticate(module = AuthModule.GUEST_USER, permissions = {AuthPermissions.GUEST})
    private ResponseEntity<ApiResponse<String>> getData(HttpServletRequest request,
        @Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new ApiException(
                ApiResponseCodes.SIGNUP_FAILED_INVALID_PARAMETER.getTitle(),
                ApiResponseCodes.SIGNUP_FAILED_INVALID_PARAMETER.getMessage() + ErrorUtils.getBeanErrors(result),
                ApiResponseCodes.SIGNUP_FAILED_INVALID_PARAMETER);
        }

        authService.signUp(signUpRequest, request.getRemoteAddr());
        return new ResponseEntity<>(
            new ApiResponse<>(true, ApiResponseCodes.SIGNUP_SUCCESS.getCode(),
                ApiResponseCodes.SIGNUP_SUCCESS.getMessage(),
                null),
            HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    @AuthUpgrade(module = AuthModule.AUTH_USER, overrideUpgradePermissions = true)
    @Authenticate(module = AuthModule.GUEST_USER, permissions = {AuthPermissions.GUEST})
    private ResponseEntity<ApiResponse<String>> login(HttpServletRequest request,
        @Valid @RequestBody LoginRequest loginRequest, BindingResult result) throws Exception {

        if (result.hasErrors()) {
            throw new ApiException(
                ApiResponseCodes.LOGIN_FAILED_INVALID_PARAMS.getTitle(),
                ApiResponseCodes.LOGIN_FAILED_INVALID_PARAMS.getMessage() + ErrorUtils.getBeanErrors(result),
                ApiResponseCodes.LOGIN_FAILED_INVALID_PARAMS);
        }
        String token = authService.login(loginRequest, request.getRemoteAddr());
        request.setAttribute(UtilConstants.AUTH_TOKEN, token);
        return new ResponseEntity<>(
            new ApiResponse<>(true, ApiResponseCodes.LOGIN_SUCCESS.getCode(),
                ApiResponseCodes.LOGIN_SUCCESS.getMessage(),
                null),
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
