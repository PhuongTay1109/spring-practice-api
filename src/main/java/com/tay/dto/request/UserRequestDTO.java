package com.tay.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO implements Serializable {
	
	private String firstName;
	private String className;
	private String email;
	private String phone;

}
