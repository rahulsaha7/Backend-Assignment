package com.example.backend_assingment.dto.repository;

import com.example.backend_assingment.dto.Entities.EApiLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ApiLogRepository implements IApiLogRepository{

    @PersistenceContext
    private EntityManager requestLogEntityManager;

    @Override
    public void save(EApiLog requsetLog) {
        requestLogEntityManager.persist(requsetLog);
    }
}
