package com.tfg.truby_writer.rest.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDto {
    
    private String globalError;
    private List<FieldErrorDto> fieldErrors;

    public ErrorsDto(String globalError) {
        this.globalError = globalError;
        this.fieldErrors = null;
    }

    public ErrorsDto(List<FieldErrorDto> fieldErrors) {
        this.globalError = null;
        this.fieldErrors = fieldErrors;
    }
}