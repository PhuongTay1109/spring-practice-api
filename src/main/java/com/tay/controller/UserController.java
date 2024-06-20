package com.tay.controller;

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

@RestController
@RequestMapping("/user")
public class UserController {
	
	@PostMapping("/")
	public String addUser(@RequestBody UserRequestDTO userDTO) {
		return "User added";
	}
	
	@PutMapping("/{userId}")
	public String updateUser(@PathVariable int userId, @RequestBody UserRequestDTO userDTO) {
		return "User updated";
	}
	
    @PatchMapping("/{userId}")
    public String updateStatus(@PathVariable int userId, @RequestParam(required = false) boolean status) {
        return "User Status changed";
    }
    
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId){
        return "User deleted";
    }

    @GetMapping("/{userId}")
    public UserRequestDTO getUser(@PathVariable int userId) {
        return new UserRequestDTO("Tay", "Java", "admin@tayjava.vn", "0123456789");
    }
    

}
