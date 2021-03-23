package fr.formation.masterpieceApi.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/*
 * Error informations encapsulation:
 * the status code of the error,
 * the default message given by the exception,
 * default errors thrown by the exception.
 */
class CustomError {

    private HttpStatus status;

    private String message;

    private List<String> errors;

    public CustomError(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public CustomError(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
	return status;
    }

    @Override
    public String toString() {
        return "CustomError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}