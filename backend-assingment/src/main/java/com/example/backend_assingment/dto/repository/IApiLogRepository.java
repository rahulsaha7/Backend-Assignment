package com.example.backend_assingment.dto.repository;

import com.example.backend_assingment.dto.Entities.EApiLog;

public interface IApiLogRepository {

    void save(EApiLog requsetLog);
}
