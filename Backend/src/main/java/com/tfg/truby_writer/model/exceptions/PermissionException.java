package com.tfg.truby_writer.model.exceptions;

public class PermissionException extends Exception {
    
    public PermissionException() {
        super("No tienes permisos para realizar esta operación.");
    }

    public PermissionException(String message) {
        super(message);
    }
}