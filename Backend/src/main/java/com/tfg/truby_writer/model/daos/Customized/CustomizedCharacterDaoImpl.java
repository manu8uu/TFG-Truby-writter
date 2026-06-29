package com.tfg.truby_writer.model.daos.Customized;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.tfg.truby_writer.model.entities.Character; 

public class CustomizedCharacterDaoImpl implements CustomizedCharacterDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    private String[] getTokens(String keywords) {
        if (keywords == null || keywords.isBlank()) { 
            return new String[0];
        }
        return keywords.trim().split("\\s+");
    }

    @Override
    public Slice<Character> find(Long projectId, String name, int page, int size) {
        
        String[] tokens = getTokens(name);
        List<String> conditions = new ArrayList<>();

        if (projectId != null) {
            conditions.add("c.project.id = :projectId"); 
        }
        
        for (int i = 0; i < tokens.length; i++) {
            conditions.add("LOWER(c.name) LIKE LOWER(:token" + i + ")");
        }
        
        StringBuilder queryString = new StringBuilder("SELECT c FROM Character c"); 
        if (!conditions.isEmpty()) {
            queryString.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        queryString.append(" ORDER BY c.name");
        
        TypedQuery<Character> query = entityManager.createQuery(queryString.toString(), Character.class)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);
        
        if (projectId != null) {
            query.setParameter("projectId", projectId);
        }

        for (int i = 0; i < tokens.length; i++) {
            query.setParameter("token" + i, "%" + tokens[i] + "%");
        }
        
        List<Character> characters = new ArrayList<>(query.getResultList());
        boolean hasNext = characters.size() == (size + 1);
        
        if (hasNext) {
            characters.remove(characters.size() - 1);
        }
        
        return new SliceImpl<>(characters, PageRequest.of(page, size), hasNext);
    }
}