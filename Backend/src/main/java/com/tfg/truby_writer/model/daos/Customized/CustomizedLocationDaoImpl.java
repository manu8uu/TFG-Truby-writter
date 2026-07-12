package com.tfg.truby_writer.model.daos.Customized;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import com.tfg.truby_writer.model.entities.Location;

public class CustomizedLocationDaoImpl implements CustomizedLocationDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    private String[] getTokens(String keywords) {
        if (keywords == null || keywords.isBlank()) { 
            return new String[0];
        }
        return keywords.trim().split("\\s+");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<Location> find(Long plotId, String text, int page, int size) {
        
        String[] tokens = getTokens(text);
        List<String> conditions = new ArrayList<>();
        
        if (plotId != null) {
            conditions.add("l.plot.id = :plotId"); 
        }
        
        for (int i = 0; i < tokens.length; i++) {
            conditions.add("LOWER(l.name) LIKE LOWER(:token" + i + ")");
        }
        
        StringBuilder queryString = new StringBuilder("SELECT l FROM Location l"); 
        if (!conditions.isEmpty()) {
            queryString.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        queryString.append(" ORDER BY l.name"); 
        
        Query query = entityManager.createQuery(queryString.toString())
                .setFirstResult(page * size)
                .setMaxResults(size + 1);
        
        if (plotId != null) {
            query.setParameter("plotId", plotId);
        }
        
        for (int i = 0; i < tokens.length; i++) {
            query.setParameter("token" + i, "%" + tokens[i] + "%");
        }
        
        List<Location> locations = query.getResultList();
        boolean hasNext = locations.size() == (size + 1);
        
        if (hasNext) {
            locations.remove(locations.size() - 1);
        }
        
        return new SliceImpl<>(locations, PageRequest.of(page, size), hasNext);
    }
}