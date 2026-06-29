package com.tfg.truby_writer.model.daos.Customized;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import com.tfg.truby_writer.model.entities.User;


public class CustomizedUserDaoImpl implements CustomizedUserDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    private String[] getTokens(String keywords) {
        if (keywords == null || keywords.trim().length() == 0) { 
            return new String[0];
        } else {
            return keywords.split("\\s+"); 
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<User> find(Boolean blocked, String text, int page, int size) {
        
        String[] tokens = getTokens(text);
        StringBuilder queryString = new StringBuilder("SELECT p FROM User p"); 
        
        if (blocked != null || tokens.length > 0) {
            queryString.append(" WHERE ");
        }
        
        if (blocked != null) {
            queryString.append("p.blocked = :blocked");
        }
        
        if (tokens.length != 0) {
            if (blocked != null) {
                queryString.append(" AND ");
            }
            
            for (int i = 0; i < tokens.length - 1; i++) {
                queryString.append("LOWER(p.username) LIKE LOWER(:token").append(i).append(") AND ");
            }
            
            queryString.append("LOWER(p.username) LIKE LOWER(:token").append(tokens.length - 1).append(")");
        }
        
        queryString.append(" ORDER BY p.username");
        
        Query query = entityManager.createQuery(queryString.toString())
                .setFirstResult(page * size)
                .setMaxResults(size + 1);
        
        if (blocked != null) {
            query.setParameter("blocked", blocked);
        }
        
        if (tokens.length != 0) {
            for (int i = 0; i < tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }
        
        List<User> users = query.getResultList();
        boolean hasNext = users.size() == (size + 1);
        
        if (hasNext) {
            users.remove(users.size() - 1);
        }
        
        return new SliceImpl<>(users, PageRequest.of(page, size), hasNext);
    }
}