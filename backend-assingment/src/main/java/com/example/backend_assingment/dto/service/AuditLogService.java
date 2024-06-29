package com.example.backend_assingment.dto.service;

import com.example.backend_assingment.dto.Entities.EAuditLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class AuditLogService implements IAuditLogService{

    @PersistenceContext
    private EntityManager auditLogEntityManager;

    @Override
    public void log(EAuditLog EAuditLog) {
        auditLogEntityManager.persist(EAuditLog);
    }
}
