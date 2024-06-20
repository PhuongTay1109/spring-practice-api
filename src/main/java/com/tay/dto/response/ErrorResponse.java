package com.tay.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	private Date timestamp;
	private int status;
	private String path;
	private String error;
	private String message;

}
