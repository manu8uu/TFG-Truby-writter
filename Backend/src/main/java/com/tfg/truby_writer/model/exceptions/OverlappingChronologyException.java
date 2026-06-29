package com.tfg.truby_writer.model.exceptions;

public class OverlappingChronologyException extends Exception {

    private final Integer conflictingOrder;

    public OverlappingChronologyException(Integer conflictingOrder) {
        super("Ya existe un evento en la posición cronológica " + conflictingOrder + ". Cada evento debe tener un orden único.");
        this.conflictingOrder = conflictingOrder;
    }

    public Integer getConflictingOrder() { return conflictingOrder; }
}