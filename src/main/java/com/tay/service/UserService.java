package com.tay.service;

import com.tay.dto.request.UserDTO;
import com.tay.dto.response.UserDetailResponse;
import com.tay.model.UserStatus;

public interface UserService {
	
	long saveUser(UserDTO request);
	
	void updateUser(long userId, UserDTO request);
	
	void changeStatus(long userId, UserStatus status);
	
	void deleteUser(long userId);
	
	// tạo UserDetailResponse để ví dụ chỉ trả về 1 phần của user thôi
	UserDetailResponse getUser(long userId);

}
