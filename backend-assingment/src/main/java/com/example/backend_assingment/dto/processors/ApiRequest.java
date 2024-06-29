package com.example.backend_assingment.dto.processors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {
    private Long userId;
    private String requestBody;
}
