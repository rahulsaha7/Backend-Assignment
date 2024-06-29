package com.example.backend_assingment.dto.repository;

import com.example.backend_assingment.dto.Entities.EUserActionAttempt;

public interface IUserActionAttemptRepository {
    void updateAttempt(String userName, int attemptCount);
    void create(EUserActionAttempt userActionAttempt);
    EUserActionAttempt findByUserNameOrIP(String userName, String clientIp);
}
