package com.example.backend_assingment.uesr_profile.controller;

import com.example.backend_assingment.auth_module.model.SignUpRequest;
import com.example.backend_assingment.dto.AuthPermissions;
import com.example.backend_assingment.dto.Entities.ApiResponse;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.AuthModule;
import com.example.backend_assingment.dto.annotaions.AuthUpgrade;
import com.example.backend_assingment.dto.annotaions.Authenticate;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.uesr_profile.model.UpdateUserDataRequest;
import com.example.backend_assingment.uesr_profile.model.UpdateUserDataResponse;
import com.example.backend_assingment.uesr_profile.service.IUserProfileService;
import com.example.backend_assingment.utils.CommonValidators;
import com.example.backend_assingment.utils.ErrorUtils;
import com.example.backend_assingment.utils.UtilConstants;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import io.jsonwebtoken.lang.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/backend_assignment/user-profile")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserProfileController {

    private IUserProfileService userProfileService;

    @PatchMapping("/update")
    @Authenticate(module = AuthModule.AUTH_USER, permissions = {AuthPermissions.AUTHENTICATED})
    @AuthUpgrade(module = AuthModule.AUTH_USER, overrideUpgradePermissions = true)
    private ResponseEntity<ApiResponse<UpdateUserDataResponse>> updateUserData(HttpServletRequest request,
        @RequestBody UpdateUserDataRequest updateUserDataRequest) throws Exception {

        if (updateUserDataRequest.isEmpty()){
            throw new ApiException(
                    ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER.getTitle(),
                    "At least one parameter (firstName, lastName, mobile, password) must be provided",
                    ApiResponseCodes.UPDATE_FAILED_INVALID_PARAMETER,
                    null);
        }

        validateUpdateRequest(updateUserDataRequest);

        updateUserDataRequest.setUserName(request.getAttribute(UtilConstants.AUTH_USER_ATTRIBUTE).toString()) ;

        UpdateUserDataResponse response = userProfileService.updateUserProfile(updateUserDataRequest);

        request.setAttribute(UtilConstants.AUTH_TOKEN, response.getToken());
        response.setToken(null);

        return new ResponseEntity<>(
            new ApiResponse<>(true, ApiResponseCodes.UPDATE_SUCCESS.getCode(),
                ApiResponseCodes.UPDATE_SUCCESS.getMessage(),
                response),
            HttpStatus.OK);
    }

    private void validateUpdateRequest(UpdateUserDataRequest updateUserDataRequest) {

        CommonValidators.validateFirstName(updateUserDataRequest.getFirstName());
        CommonValidators.validateLastName(updateUserDataRequest.getLastName());
        CommonValidators.validateMobile(updateUserDataRequest.getMobile());
        CommonValidators.validatePassword(updateUserDataRequest.getPassword());

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
