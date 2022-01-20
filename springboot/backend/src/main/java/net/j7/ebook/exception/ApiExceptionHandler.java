package net.j7.ebook.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Clase que detecta/modifica las excepciones propias, o de spring.
 */
@RequiredArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler {

    /**
     * Una respuesta a la peticón, con error conforme a que el usuario este desautorizado.
     *
     * @param e Datos de la excepción.
     * @return Respuesta personalizada con un cuerpo, y un estatus.
     */
    @ExceptionHandler(value = {ApiUnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(ApiUnauthorizedException e) {
        HttpStatus unauthorized = HttpStatus.LOCKED;
        ApiException unauthorizedException = new ApiException("You cannot access the resource.", e.getMessage(), unauthorized,
                ZonedDateTime.now(ZoneId.of("America/Mexico_City")));
        return new ResponseEntity<>(unauthorizedException, unauthorized);
    }

}
