package com.tay.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.tay.dto.response.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ ConstraintViolationException.class, 
						MissingServletRequestParameterException.class,
						MethodArgumentNotValidException.class 
					  })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(Exception e, WebRequest request) {
		ErrorResponse errResp = new ErrorResponse();
		errResp.setTimestamp(new Date());
		errResp.setStatus(HttpStatus.BAD_REQUEST.value());
		errResp.setPath(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();
        if (e instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[") + 1;
            int end = message.lastIndexOf("]") - 1;
            message = message.substring(start, end);
            errResp.setError("Invalid Payload");
            errResp.setMessage(message);
        } else if (e instanceof MissingServletRequestParameterException) {
        	errResp.setError("Invalid Parameter");
        	errResp.setMessage(message);
        } else if (e instanceof ConstraintViolationException) {
        	errResp.setError("Invalid Parameter");
        	errResp.setMessage(message.substring(message.indexOf(" ") + 1));
        } else {
        	errResp.setError("Invalid Data");
        	errResp.setMessage(message);
        }

		return errResp;
	}
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e, WebRequest request) {
        ErrorResponse errResp = new ErrorResponse();
        errResp.setTimestamp(new Date());
        errResp.setPath(request.getDescription(false).replace("uri=", ""));
        errResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errResp.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errResp.setMessage(e.getMessage());

        return errResp;
    }

	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
	 public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setMessage(e.getMessage());

        return errorResponse;
    }
}
