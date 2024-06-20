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

import com.tay.dto.request.UserRequestDTO;
import com.tay.dto.response.ResponseDTO;
import com.tay.dto.response.ResponseFailure;
import com.tay.dto.response.ResponseSuccess;
import com.tay.model.UserStatus;
import com.tay.model.UserType;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
	
	// hỗ trợ viết document bằng OPEN API
	@Operation(method = "POST", summary = "Add new user", description = "Send a request via this API to create new user")
    @PostMapping(value = "/")
    public ResponseDTO<Integer> addUser(@Valid @RequestBody UserRequestDTO user) {
        return new ResponseDTO<>(HttpStatus.CREATED.value(), "user added successfully", 1);
    }

//    @PostMapping("/")
//    public ResponseSuccess addUser(@Valid @RequestBody UserRequestDTO user) {
//        try {
//            return new ResponseSuccess(HttpStatus.CREATED, "User added successfully,", 1);
//        } catch (Exception e) {
//            return new ResponseFailure(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }

    @PutMapping("/{userId}")
    public ResponseSuccess updateUser(@PathVariable @Min(1) int userId, @Valid @RequestBody UserRequestDTO user) {
        try {
            return new ResponseSuccess(HttpStatus.ACCEPTED, "User updated successfully");
        } catch (Exception e) {
            return new ResponseFailure(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/{userId}")
    public ResponseSuccess updateStatus(@Min(1) @PathVariable int userId, @RequestParam boolean status) {
        try {
            return new ResponseSuccess(HttpStatus.ACCEPTED, "User's status changed successfully");
        } catch (Exception e) {
            return new ResponseFailure(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseSuccess deleteUser(@PathVariable @Min(value = 1, message = "userId must be greater than 0") int userId) {
        try {
            return new ResponseSuccess(HttpStatus.NO_CONTENT, "User deleted successfully");
        } catch (Exception e) {
            return new ResponseFailure(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseSuccess getUser(@PathVariable @Min(1) int userId) {
        
        try {
            return new ResponseSuccess(HttpStatus.OK, "user", new UserRequestDTO("Tay", "Java", "admin@tayjava.vn", "0123456789", UserStatus.ACTIVE, UserType.ADMIN, null, "PhuongTay", "phuongtay", null));
        } catch (Exception e) {
            return new ResponseFailure(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
