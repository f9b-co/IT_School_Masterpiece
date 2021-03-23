package fr.formation.masterpieceApi.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/*
 * Custom controller advice to handle all RestController exceptions.
 *
 * Manages handlers for exceptions to mutualize and standardize exception handling.
 */
@RestControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private ResponseEntity<Object> customResponse(CustomError customError) {
		return new ResponseEntity<>(customError.toString(), new HttpHeaders(), customError.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		String message = "Unexpected error";
		CustomError customError = new CustomError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), message);
		return customResponse(customError);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> accesDenied(AccessDeniedException ex) {
		CustomError customError = new CustomError(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), ex.getMessage());
		return customResponse(customError);
	}

	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex) {
		CustomError customError = new CustomError(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getLocalizedMessage());
		return customResponse(customError);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
	List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " "
				+ violation.getPropertyPath() + ": " + violation.getMessage());
		}
		CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return customResponse(customError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
			List<String> errors = new ArrayList<>();
			for (FieldError error : ex.getBindingResult().getFieldErrors()) {
				errors.add(error.getField() + ": " + error.getCode());
			}
			for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
				errors.add(error.getObjectName() + ": " + error.getCode());
			}
			CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
			return handleExceptionInternal(ex, customError, headers, customError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
			StringBuilder builder = new StringBuilder();
			builder.append(ex.getMethod());
			builder.append(" Request method not supported. Supported methods are ");
			ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
			CustomError customError = new CustomError(HttpStatus.METHOD_NOT_ALLOWED,
				ex.getLocalizedMessage(), builder.toString());
			return customResponse(customError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		 HttpHeaders headers, HttpStatus status, WebRequest request) {
			String error = "JsonParseError";
			CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
			return customResponse(customError);
    }
}
