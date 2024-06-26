package com.tay.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

public class SuccessResponse extends ResponseEntity<SuccessResponse.Payload> {
	
	// PUT, PATCH, DELETE
	public SuccessResponse(HttpStatus status, String messsage) {
		super(new Payload(status.value(), messsage), HttpStatus.OK);
	}
	
	// GET, POST
	public SuccessResponse(HttpStatus status, String messsage, Object data) {
		super(new Payload(status.value(), messsage, data), status);
	}

	@Data
	@AllArgsConstructor
	public static class Payload {
		private final int status;
		private final String message;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private Object data;
		
		public Payload(int status, String message) {
			this.status = status;
			this.message = message;
		}	
	}

}
