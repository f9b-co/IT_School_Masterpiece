package fr.formation.masterpieceApi.exceptions;

/*
 * Exception thrown if resource is not found.
 * Used to handle 404 Http error.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
	//
    }

    public ResourceNotFoundException(String message) {
	super(message);
    }
}
