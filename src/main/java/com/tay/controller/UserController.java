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
import com.tay.model.UserStatus;
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
    	return new SuccessResponse(HttpStatus.OK, "Get user info successfully", userService.getUser(userId));    	
    }
    
    // Spring boot phân trang bắt đầu từ 0
    // ko bắt buộc các param
    // mobile min = 10
    // sortBy=firstName:asc => cắt chuỗi cái này để xử lý sort tăng dần hay giảm dần
    @GetMapping("/list")
    public SuccessResponse getAllUsers(@RequestParam(defaultValue = "0", required = false) int pageNo,
    									@Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
    									@RequestParam(required = false) String sortBy) {
    	return new SuccessResponse(HttpStatus.OK, "Get users info successfully", userService.getAllUsersWithSortBy(pageNo, pageSize, sortBy));
    }
    
    @GetMapping("/list-with-sort-by-multiple-columns")
    public SuccessResponse getAllUsersWithSortByMutipleColumns(@RequestParam(defaultValue = "0", required = false) int pageNo,
    															@RequestParam(defaultValue = "20", required = false) int pageSize,
    															@RequestParam(required = false) String... sortBy) {
    	return new SuccessResponse(HttpStatus.OK, "Get users info successfully", userService.getAllUsersWithSortByMutipleColumns(pageNo, pageSize, sortBy));
    }
    
    // Customize query
    @GetMapping("/list-with-sort-by-column-search")
    public SuccessResponse getAllUsersWithSortByColumnAndSearch(@RequestParam(defaultValue = "0", required = false) int pageNo,
    															@RequestParam(defaultValue = "20", required = false) int pageSize,
    															@RequestParam(defaultValue = "20", required = false) String search,
    															@RequestParam(required = false) String sortBy) {
    	return new SuccessResponse(HttpStatus.OK, "Get users info successfully", userService.getAllUsersWithSortByColumnAndSearch(pageNo, pageSize,search, sortBy));
    }

}
