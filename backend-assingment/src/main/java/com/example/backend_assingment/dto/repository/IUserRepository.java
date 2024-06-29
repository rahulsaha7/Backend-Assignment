package com.example.backend_assingment.dto.repository;

import com.example.backend_assingment.dto.Entities.EUser;
import java.util.Optional;

public interface IUserRepository {
     void createUser(EUser eUser);
     void update(EUser eUser);
     Optional<EUser> findByUserName(String username);
     Optional<EUser> findByUserEmail(String email);
}
