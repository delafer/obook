package net.j7.ebook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Modelo de datos para excepciones.
 */
@Getter
@AllArgsConstructor
public class ApiException {

    private final String message;
    private final String details;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}