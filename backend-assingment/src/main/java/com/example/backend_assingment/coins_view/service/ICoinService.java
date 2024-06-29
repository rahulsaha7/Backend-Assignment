package com.example.backend_assingment.coins_view.service;

import com.example.backend_assingment.coins_view.model.CoinViewApiResponse;

public interface ICoinService {

    CoinViewApiResponse searchCoinsByName(String searchSymbol, String userName);
}
