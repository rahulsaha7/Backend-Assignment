package com.example.backend_assingment.dto.annotaions;

import com.example.backend_assingment.dto.service.SessionAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SessionAuthResponseAdvice implements ResponseBodyAdvice<Object> {

    private SessionAuthService sessionAuthService;

    @Override
    public boolean supports(MethodParameter param, Class<? extends HttpMessageConverter<?>> clazz) {

        return param.getMethod().isAnnotationPresent(AuthUpgrade.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter param, MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> clazz, ServerHttpRequest request, ServerHttpResponse response) {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        AuthUpgrade authEntity = param.getMethod().getAnnotation(AuthUpgrade.class);

        sessionAuthService.handleResponse(servletRequest, servletResponse, authEntity);

        return body;
    }

}
