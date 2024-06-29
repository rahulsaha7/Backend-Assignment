package com.example.backend_assingment.coins_view.model;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinViewRequest {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should only contain alphabetical characters")
    private String symbol;
}
