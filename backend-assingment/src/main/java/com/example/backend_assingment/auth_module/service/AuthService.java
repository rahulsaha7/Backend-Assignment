package com.example.backend_assingment.auth_module.service;

import com.example.backend_assingment.auth_module.model.LoginRequest;
import com.example.backend_assingment.auth_module.model.SignUpRequest;
import com.example.backend_assingment.dto.AuthPermissions;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.EUser;
import com.example.backend_assingment.dto.Entities.EUserActionAttempt;
import com.example.backend_assingment.dto.exception.ApiException;
import com.example.backend_assingment.dto.repository.IUserActionAttemptRepository;
import com.example.backend_assingment.dto.repository.IUserRepository;
import com.example.backend_assingment.utils.PasswordUtil;
import com.example.backend_assingment.utils.TokenUtils;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AuthService implements IAuthService{

    private static final int ALLOWED_LOGIN_ATTEMPTS = 3;

    private IUserRepository authRepository;
    private TokenUtils tokenUtils;
    private IUserActionAttemptRepository userActionAttemptRepsitory;

    @Override
    public void signUp(SignUpRequest request, String clientIp) {
        validateSignUpRequest(request);
        EUser userData = getUserData(request);
        authRepository.createUser(userData);
        createUserActionAttemptEntry(userData, clientIp);
    }

    private void createUserActionAttemptEntry(EUser userData, String clientIp) {
        userActionAttemptRepsitory.create(
            new EUserActionAttempt(
                userData.getUsername(),
                0,
                clientIp
            )
        );
    }

    @Override
    public String login(LoginRequest loginRequest, String clientIp) throws Exception{
        EUser userData = validateLoginRequestAndGetUserData(loginRequest, clientIp);
        return tokenUtils.generateToken(
            userData.getUsername(),
            userData.getFirstName(),
            userData.getLastName(),
            userData.getEmail(),
            userData.getMobile(),
            AuthPermissions.AUTHENTICATED
        );

    }

    private EUser validateLoginRequestAndGetUserData(LoginRequest loginRequest, String clientIp) {

        validateUserActionAttempt(loginRequest, clientIp);

        Optional<EUser> oEUser = authRepository.findByUserName(loginRequest.getUsername());
        if(oEUser.isEmpty()){

            throw new ApiException(ApiResponseCodes.LOGIN_FAILED_INVALID_CREDENTIALS.getTitle(),
                ApiResponseCodes.LOGIN_FAILED_INVALID_CREDENTIALS.getMessage(),
                ApiResponseCodes.LOGIN_FAILED_INVALID_CREDENTIALS);
        }
        if (!PasswordUtil.matchPassword(
            loginRequest.getPassword(),
            oEUser.get().getPassword()
        )) {

            throw new ApiException(ApiResponseCodes.LOGIN_FAILED_INVALID_CREDENTIALS.getTitle(),
                ApiResponseCodes.LOGIN_FAILED_INVALID_CREDENTIALS.getMessage(),
                ApiResponseCodes.LOGIN_FAILED_INVALID_CREDENTIALS);
        }

        return oEUser.get();

    }

    private void validateUserActionAttempt(LoginRequest loginRequest, String clientIp) {
        EUserActionAttempt userActionAttempt = userActionAttemptRepsitory.findByUserNameOrIP(loginRequest.getUsername(),clientIp);
        if(userActionAttempt.getActionAttemptCount() > ALLOWED_LOGIN_ATTEMPTS){
            throw new ApiException(ApiResponseCodes.ACCOUNT_BLOCKED.getTitle(),
                ApiResponseCodes.ACCOUNT_BLOCKED.getMessage(),
                ApiResponseCodes.ACCOUNT_BLOCKED);
        }
        userActionAttemptRepsitory.updateAttempt(userActionAttempt.getUsername(), userActionAttempt.getActionAttemptCount() + 1);
    }

    private void validateSignUpRequest(SignUpRequest request) {
        authRepository.findByUserEmail(request.getEmail()).ifPresent(eUser -> {
            throw new ApiException(ApiResponseCodes.SIGNUP_FAILED_DUPLICATE_EMAIL.getTitle(),
                ApiResponseCodes.SIGNUP_FAILED_DUPLICATE_EMAIL.getMessage(),
                ApiResponseCodes.SIGNUP_FAILED_DUPLICATE_EMAIL);
        });
        authRepository.findByUserName(request.getUsername()).ifPresent(eUser -> {
            throw new ApiException(ApiResponseCodes.SIGNUP_FAILED_DUPLICATE_USERNAME.getTitle(),
                ApiResponseCodes.SIGNUP_FAILED_DUPLICATE_USERNAME.getMessage(),
                ApiResponseCodes.SIGNUP_FAILED_DUPLICATE_USERNAME);
        });
    }

    private EUser getUserData(SignUpRequest request) {
        EUser eUser = new EUser();
        eUser.setEmail(request.getEmail());
        eUser.setFirstName(request.getFirstName());
        eUser.setLastName(request.getLastName());
        eUser.setPassword(PasswordUtil.encryptPassword(request.getPassword()));
        eUser.setMobile(request.getMobile());
        eUser.setUsername(request.getUsername());
        return eUser;
    }


}
