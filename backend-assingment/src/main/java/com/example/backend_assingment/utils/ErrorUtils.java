package com.example.backend_assingment.utils;

import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;


public class ErrorUtils {

    private ErrorUtils() {}

    public static String getBeanErrors(BindingResult result) {
        return result.getFieldErrors().stream()
            .map(x -> new StringBuilder()
                .append(String.format("Field Name: %s, ", x.getField()))
                .append(String.format("Rejected Value: %s, ", x.getRejectedValue()))
                .append(String.format("Error Msg: %s", x.getDefaultMessage()))
                .toString())
            .collect(Collectors.joining("; "));
    }

}
