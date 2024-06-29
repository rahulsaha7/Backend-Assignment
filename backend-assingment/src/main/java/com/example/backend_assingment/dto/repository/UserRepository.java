package com.example.backend_assingment.dto.repository;

import com.example.backend_assingment.dto.Entities.EUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Repository
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserRepository implements IUserRepository {

    @PersistenceContext
    private EntityManager userAuthEntityManager;

    private static final String FIND_USER_BY_USERNAME = """
                SELECT u FROM EUser u WHERE username = :username
        """;

    private static final String FIND_USER_BY_EMAIL = """
        SELECT u FROM EUser u WHERE email = :email
""";

    private static final String UPDATE_QUERY = """ 
         UPDATE users 
         SET first_name = :firstName, 
         last_name = :lastName, 
         mobile = :mobile, 
         password = :password 
         WHERE username = :userName """
        ;



    @Override
    public void createUser(EUser eUser) {
        try{
            userAuthEntityManager.persist(eUser);
        } catch (Exception e) {
            log.error("Error while creating user: {}", e.getMessage());
        }
    }

    @Override
    public void update(EUser eUser) {
        Query query = userAuthEntityManager.createNativeQuery(UPDATE_QUERY);
        query.setParameter("firstName", eUser.getFirstName());
        query.setParameter("lastName", eUser.getLastName());
        query.setParameter("mobile", eUser.getMobile());
        query.setParameter("password", eUser.getPassword());
        query.setParameter("userName", eUser.getUsername());
        query.executeUpdate();
    }

    @Override
    public Optional<EUser> findByUserName(String username) {
        try{
            return Optional.of(userAuthEntityManager.createQuery(FIND_USER_BY_USERNAME, EUser.class)
                .setParameter("username", username)
                .getSingleResult());
        } catch (NoResultException e) {
           return Optional.empty();
        }
    }

    @Override
    public Optional<EUser> findByUserEmail(String email) {
        try{
            return Optional.of(userAuthEntityManager.createQuery(FIND_USER_BY_EMAIL, EUser.class)
                .setParameter("email", email)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
