package com.example.backend_assingment.auth_module.service;

import com.example.backend_assingment.auth_module.model.LoginRequest;
import com.example.backend_assingment.auth_module.model.SignUpRequest;

public interface IAuthService {
      void signUp(SignUpRequest request, String clientIp);

      String login(LoginRequest loginRequest, String clientIp) throws Exception;
}
