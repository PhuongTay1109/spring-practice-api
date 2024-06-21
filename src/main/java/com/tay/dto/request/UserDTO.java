package com.tay.dto.request;

import java.sql.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tay.model.Gender;
import com.tay.model.UserStatus;
import com.tay.model.UserType;
import com.tay.util.EnumValue;
import com.tay.util.PhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserDTO {

	@NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NotNull(message = "lastName must be not null") 
    private String lastName;

    @Email(message = "email invalid format") 
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @EnumValue(name = "gender", enumClass = Gender.class)
    private Gender gender;

    @NotNull(message = "username must be not null")
    private String username;

    private String password;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    private UserType type;

    @NotEmpty(message = "addresses can not empty")
    private Set<AddressDTO> addresses;

    @EnumValue(name = "status", enumClass = UserStatus.class)
    private UserStatus status;

    public UserDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
