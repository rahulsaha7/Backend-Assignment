package com.example.backend_assingment.dto.annotaions;

import com.example.backend_assingment.configs.BasicConfiguration;
import com.example.backend_assingment.dto.AuthPermissions;
import com.example.backend_assingment.dto.Entities.ApiResponse;
import com.example.backend_assingment.dto.Entities.ApiResponseCodes;
import com.example.backend_assingment.dto.Entities.EAuditLog;
import com.example.backend_assingment.dto.repository.IUserRepository;
import com.example.backend_assingment.dto.service.AuditLogService;
import com.example.backend_assingment.utils.CookieUtils;
import com.example.backend_assingment.utils.JsonUtils;
import com.example.backend_assingment.utils.TokenUtils;
import com.example.backend_assingment.utils.UtilConstants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private IUserRepository authRepository;

    @Autowired
    private BasicConfiguration basicConfiguration;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Authenticate authenticate = handlerMethod.getMethodAnnotation(Authenticate.class);

            if (authenticate != null) {
                auditLog(request, response);
                switch (authenticate.module()) {
                    case AUTH_USER:
                        return handleAuthUser(request, response, authenticate.permissions());
                    case GUEST_USER:
                        return handleGuestUser(request, response);
                    default:
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Unknown Module");
                        return false;
                }
            }
        }
        return true;
    }

    private void auditLog(HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        String clientId = request.getHeader("Client-ID");
        String username;
        try {
            username = extractTokenFromCookies(request) == null ? null
                : tokenUtils.getUsernameFromToken(extractTokenFromCookies(request));
        }catch (ExpiredJwtException e) {
            CookieUtils.deleteCookie(response, UtilConstants.X_PO_AUTH);
            username = null;
        }

        StringBuilder additionalInfoBuilder = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            additionalInfoBuilder.append(headerName).append(": ").append(headerValue).append("\n");
        }
        String additionalInfo = additionalInfoBuilder.toString();

        auditLogService.log(new EAuditLog(
            requestUri,
            clientId,
            username,
            additionalInfo,
            request.getHeader("User-Agent"),
            request.getRemoteAddr()
        ));
    }


    private boolean handleAuthUser(HttpServletRequest request, HttpServletResponse response,
        AuthPermissions[] permissions) throws Exception {
        String token = extractTokenFromCookies(request);
        if (token == null || !tokenUtils.validateToken(token, permissions)) {
            sendFailureResponse(response, getAuthFailureResponse("Unauthorized"));
            // Blacklist the token if necessary
            return false;
        }

        String username = tokenUtils.getUsernameFromToken(token);
        if (authRepository.findByUserName(username).isEmpty()) {
            sendFailureResponse(response, getAuthFailureResponse("Unauthorized"));
            return false;
        }

        String originHeader = request.getHeader(UtilConstants.X_PO_ORIGIN);
        if (originHeader == null || !basicConfiguration.getPredefinedOrigin().equals(originHeader)) {
            sendFailureResponse(response, getAuthFailureResponse("Invalid Origin"));
            return false;
        }

        request.setAttribute(UtilConstants.AUTH_USER_ATTRIBUTE, username);

        return true;
    }

    private boolean handleGuestUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authKeyHeader = request.getHeader(UtilConstants.X_PO_AUTH_KEY);
        String originHeader = request.getHeader(UtilConstants.X_PO_ORIGIN);

        if (authKeyHeader == null || !basicConfiguration.getPredefinedAuthKey().equals(authKeyHeader)) {
            sendFailureResponse(response, getAuthFailureResponse("Invalid Auth Key"));
            return false;
        }

        if (originHeader == null || !basicConfiguration.getPredefinedOrigin().equals(originHeader)) {
            sendFailureResponse(response, getAuthFailureResponse("Invalid Origin"));
            return false;
        }

        return true;
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        return CookieUtils.getCookieValue(request, UtilConstants.X_PO_AUTH);
    }

    private void sendFailureResponse(HttpServletResponse response,
        ApiResponse authenticationFailureResponse) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(
            Objects.requireNonNull(JsonUtils.getJson(authenticationFailureResponse)));
    }

    private ApiResponse getAuthFailureResponse(String message) {
        return new ApiResponse<>(false, ApiResponseCodes.UNAUTHORIZED_ACCESS.getCode(),
            ApiResponseCodes.UNAUTHORIZED_ACCESS.getMessage() + message,
            null);
    }


}
