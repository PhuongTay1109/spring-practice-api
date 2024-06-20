package com.tay.dto.request;

import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tay.model.UserStatus;
import com.tay.model.UserType;
import com.tay.util.EnumValue;
import com.tay.util.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

	// error message should contain field name that is not valid
	
	@NotBlank(message = "firstName must be not blank")
	private String firstName;

	@NotNull(message = "lastName must be not null")
	private String lastName;

	@Email(message = "email invalid format")
	private String email;

	//@Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
	@PhoneNumber
	private String phone;
	
	@NotNull(message = "status must be not null")
	@EnumValue(name = "status", enumClass = UserStatus.class)
	private UserStatus status;
	
	@NotNull(message = "type must be not null")
	@EnumValue(name = "type", enumClass = UserType.class)
	private UserType type;

	@NotNull(message = "dateOfBirth must be not null")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	private Date dateOfBirth;
	
	@NotNull(message = "username must be not null")
	private String username;
	
	@NotNull(message = "password must be not null")
	private String password;	

	@NotEmpty
	List<String> permission;
}
