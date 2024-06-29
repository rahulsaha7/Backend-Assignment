package com.example.backend_assingment.dto.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_action_attempts")
@Getter
@Setter
@NoArgsConstructor
public class EUserActionAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "action_attempt", nullable = false)
    private int actionAttemptCount;

    @Column(name = "client_ip", nullable = false)
    private String clientIp;

    public EUserActionAttempt(String username, int attemptCount, String clientIp) {
        this.username = username;
        this.actionAttemptCount = attemptCount;
        this.clientIp = clientIp;
    }

}
