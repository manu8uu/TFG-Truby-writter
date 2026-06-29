package com.tfg.truby_writer.model.exceptions;

public class InvalidPositionException extends Exception {

    private final String fieldName;
    private final Number invalidValue;

    public InvalidPositionException(String fieldName, Number invalidValue) {
        super("El valor " + invalidValue + " no es una posición válida para el campo '" + fieldName + "'. No puede ser negativo.");
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() { return fieldName; }
    public Number getInvalidValue() { return invalidValue; }
}