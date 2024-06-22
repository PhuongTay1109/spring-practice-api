package com.tay.service;

import com.tay.dto.request.UserDTO;
import com.tay.dto.response.PageResponse;
import com.tay.dto.response.UserDetailResponse;
import com.tay.model.UserStatus;

public interface UserService {
	
	long saveUser(UserDTO request);
	
	void updateUser(long userId, UserDTO request);
	
	void changeStatus(long userId, UserStatus status);
	
	void deleteUser(long userId);
	
	// tạo UserDetailResponse để ví dụ chỉ trả về 1 phần của user thôi
	UserDetailResponse getUser(long userId);
	
	PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);
	
	// String... tương ứng với List<String> => varargs|variable argument 
	// => allow to accept zero or more arguments of the specified type
	// the varargs is treated as an array within the method
	// usage: when calling the method, can provide multiple sorting criteria withou having to specify a fixed number of parameters
	PageResponse<?> getAllUsersWithSortByMutipleColumns(int pageNo, int pageSize, String... sorts);

	// customize query
	// sort 1 trường
	PageResponse<?> getAllUsersWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy);

}
