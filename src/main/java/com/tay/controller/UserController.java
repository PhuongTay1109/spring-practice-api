package com.tay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tay.dto.request.UserDTO;
import com.tay.dto.response.SuccessResponse;
import com.tay.dto.response.UserDetailResponse;
import com.tay.model.UserStatus;
import com.tay.model.UserType;
import com.tay.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;

    @PostMapping("/")
    public SuccessResponse addUser(@Valid @RequestBody UserDTO user) {
    	userService.saveUser(user);
        return new SuccessResponse(HttpStatus.CREATED, "User added successfully");
    }

    @PutMapping("/{userId}")
    public SuccessResponse updateUser(@PathVariable @Min(1) int userId, @Valid @RequestBody UserDTO user) {
    	userService.updateUser(userId, user);
        return new SuccessResponse(HttpStatus.ACCEPTED, "User updated successfully");
    }

    @PatchMapping("/{userId}")
    public SuccessResponse updateStatus(@Min(1) @PathVariable int userId, @RequestParam UserStatus status) {
    	userService.changeStatus(userId, status);
        return new SuccessResponse(HttpStatus.ACCEPTED, "User's status changed successfully");
    }

    @DeleteMapping("/{userId}")
    public SuccessResponse deleteUser(@PathVariable @Min(value = 1, message = "userId must be greater than 0") int userId) {
    	userService.deleteUser(userId);
        return new SuccessResponse(HttpStatus.NO_CONTENT, "User deleted successfully");
    }

    @GetMapping("/{userId}")
    public SuccessResponse getUser(@PathVariable @Min(1) int userId) {
    	UserDetailResponse user = userService.getUser(userId);
    	return new SuccessResponse(HttpStatus.OK, "Get user info successfully", user);
    }

}
