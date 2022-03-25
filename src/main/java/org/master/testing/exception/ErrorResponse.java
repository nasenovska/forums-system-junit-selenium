package org.master.testing.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private List<FieldError> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
