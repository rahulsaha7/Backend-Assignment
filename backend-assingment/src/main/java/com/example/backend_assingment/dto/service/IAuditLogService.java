package com.example.backend_assingment.dto.service;

import com.example.backend_assingment.dto.Entities.EAuditLog;

public interface IAuditLogService {
        void log(EAuditLog EAuditLog);
}
