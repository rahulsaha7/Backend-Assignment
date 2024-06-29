package com.example.backend_assingment.dto.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
public class EAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_uri")
    private String requestUri;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "username")
    private String username;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "client_ip_address")
    private String clientIpAddress;

    public EAuditLog(String requestUri, String clientId, String username, String additionalInfo, String userAgent, String clientIpAddress) {
        this.requestUri = requestUri;
        this.clientId = clientId;
        this.username = username;
        this.additionalInfo = additionalInfo;
        this.userAgent = userAgent;
        this.clientIpAddress = clientIpAddress;
    }
    // Getters and setters
}

