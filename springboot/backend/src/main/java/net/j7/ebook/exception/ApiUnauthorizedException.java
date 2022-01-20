package net.j7.ebook.exception;

public class ApiUnauthorizedException extends RuntimeException{

    public ApiUnauthorizedException(String message) {
        super(message);
    }

    public ApiUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
