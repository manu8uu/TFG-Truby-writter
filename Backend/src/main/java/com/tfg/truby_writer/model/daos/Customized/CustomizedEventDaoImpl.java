package com.tfg.truby_writer.model.daos.Customized;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.tfg.truby_writer.model.entities.Event;

public class CustomizedEventDaoImpl implements CustomizedEventDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    private String[] getTokens(String keywords) {
        if (keywords == null || keywords.isBlank()) { 
            return new String[0];
        }
        return keywords.trim().split("\\s+");
    }

    @Override
    public Slice<Event> find(Long lineTimeId, String title, Integer chapterNumber, Integer chronoOrder, int page, int size) {
        
        String[] tokens = getTokens(title);
        List<String> conditions = new ArrayList<>();

        // 1. Unificamos el alias a 'e' para la entidad Event
        if (lineTimeId != null) {
            // Nota: Asegúrate de que la relación en Event.java se llama 'lineTime' o 'timeline'
            conditions.add("e.lineTime.id = :lineTimeId"); 
        }
        
        // 2. Añadimos los filtros numéricos que faltaban
        if (chapterNumber != null) {
            conditions.add("e.chapterNumber = :chapterNumber");
        }
        
        if (chronoOrder != null) {
            conditions.add("e.chronoOrder = :chronoOrder");
        }
        
        for (int i = 0; i < tokens.length; i++) {
            conditions.add("LOWER(e.title) LIKE LOWER(:token" + i + ")");
        }
        
        // 3. Corregimos la entidad base: de Character a Event
        StringBuilder queryString = new StringBuilder("SELECT e FROM Event e"); 
        if (!conditions.isEmpty()) {
            queryString.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        // 4. Ordenación lógica para eventos narrativos
        queryString.append(" ORDER BY e.chapterNumber ASC, e.chronoOrder ASC");
        
        TypedQuery<Event> query = entityManager.createQuery(queryString.toString(), Event.class)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);
        
        // Inyectamos todos los parámetros
        if (lineTimeId != null) {
            query.setParameter("lineTimeId", lineTimeId);
        }
        
        if (chapterNumber != null) {
            query.setParameter("chapterNumber", chapterNumber);
        }
        
        if (chronoOrder != null) {
            query.setParameter("chronoOrder", chronoOrder);
        }

        for (int i = 0; i < tokens.length; i++) {
            query.setParameter("token" + i, "%" + tokens[i] + "%");
        }
        
        List<Event> events = new ArrayList<>(query.getResultList());
        boolean hasNext = events.size() == (size + 1);
        
        if (hasNext) {
            events.remove(events.size() - 1);
        }
        
        return new SliceImpl<>(events, PageRequest.of(page, size), hasNext);
    }
}