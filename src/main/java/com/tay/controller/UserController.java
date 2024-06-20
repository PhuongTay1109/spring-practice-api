package com.tay.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tay.dto.request.UserRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
	
	@PostMapping("/")
	public String addUser(@Valid @RequestBody UserRequestDTO userDTO) {
		return "User added";
	}
	
	@PutMapping("/{userId}")
	public String updateUser(@PathVariable int userId, @Valid @RequestBody UserRequestDTO userDTO) {
		return "User updated";
	}
	
    @PatchMapping("/{userId}")
    public String updateStatus(@PathVariable int userId, @Valid @RequestParam boolean status) {
        return "User Status changed";
    }
    
    @DeleteMapping("/{userId}")
    public String deleteUser(@Min(1) @PathVariable int userId){
        return "User deleted";
    }
//
//    @GetMapping("/{userId}")
//    public UserRequestDTO getUser(@PathVariable int userId) {
//        return new UserRequestDTO("Tay", "Java", "admin@tayjava.vn", "0123456789", "09/11/2003", ["spring"]);
//    }
    

}
