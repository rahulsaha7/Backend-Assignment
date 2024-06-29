package com.example.backend_assingment.dto.service;

import com.example.backend_assingment.dto.annotaions.AuthUpgrade;
import com.example.backend_assingment.utils.CookieUtils;
import com.example.backend_assingment.utils.UtilConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class SessionAuthService {

    public void handleResponse(HttpServletRequest request, HttpServletResponse response, AuthUpgrade authEntity) {
        String token = request.getAttribute(UtilConstants.AUTH_TOKEN).toString();
        switch (authEntity.module()) {
            case AUTH_USER:
                processAuthUpgrade(response, token);
                break;
            case GUEST_USER:
                // Write logic for gust User
                break;
            default:
                break;
        }
    }

    private void processAuthUpgrade(HttpServletResponse response, String token) {

            CookieUtils.createCookie(
                response,
                UtilConstants.X_PO_AUTH,
                token,
                60 * 60 * 24 * 365,
                false
            );
        }

}
