package com.example.backend_assingment.dto.repository;

import com.example.backend_assingment.dto.Entities.EUserActionAttempt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserActionAttemptRepository implements IUserActionAttemptRepository {

    @PersistenceContext
    private EntityManager userActionAttemptEntityManager;

    private static final String UPDATE_QUERY = """
    UPDATE user_action_attempts 
    SET action_attempt = :action_attempt
    WHERE username = :username
""";
    @Override
    public void updateAttempt(String userName, int attemptCount) {
        Query query = userActionAttemptEntityManager.createNativeQuery(UPDATE_QUERY);
        query.setParameter("action_attempt", attemptCount);
        query.setParameter("username", userName);
        query.executeUpdate();
    }

    @Override
    public void create(EUserActionAttempt userActionAttempt) {
        userActionAttemptEntityManager.persist(userActionAttempt);
    }

    @Override
    public EUserActionAttempt findByUserNameOrIP(String userName, String clientIp) {
        try {
            String queryString = "SELECT u FROM EUserActionAttempt u WHERE 1=1";

            if (userName != null) {
                queryString += " AND u.username = :username";
            }
            if (clientIp != null) {
                queryString += " AND u.clientIp = :client_ip";
            }

            TypedQuery<EUserActionAttempt> query = userActionAttemptEntityManager.createQuery(queryString, EUserActionAttempt.class);

            if (userName != null) {
                query.setParameter("username", userName);
            }
            if (clientIp != null) {
                query.setParameter("client_ip", clientIp);
            }

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
