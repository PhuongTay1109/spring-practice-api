package com.tay.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tay.dto.request.AddressDTO;
import com.tay.dto.request.UserDTO;
import com.tay.dto.response.PageResponse;
import com.tay.dto.response.UserDetailResponse;
import com.tay.exception.ResourceNotFoundException;
import com.tay.model.Address;
import com.tay.model.User;
import com.tay.model.UserStatus;
import com.tay.repository.UserRepository;
import com.tay.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor // inject
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public long saveUser(UserDTO request) {
		User user = User.builder()
						.firstName(request.getFirstName())
						.lastName(request.getLastName())
						.dateOfBirth(request.getDateOfBirth())
						.gender(request.getGender())
						.phone(request.getPhone())
						.email(request.getEmail())
						.username(request.getUsername())
						.password(request.getPassword())
						.status(request.getStatus())
						.type(request.getType()).build();
		request.getAddresses().forEach(a -> 
				user.saveAddress(Address.builder()
										.apartmentNumber(a.getApartmentNumber())
										.floor(a.getFloor())
										.building(a.getBuilding())
										.streetNumber(a.getStreetNumber())
										.street(a.getStreet())
										.city(a.getCity())
										.country(a.getCountry())
										.addressType(a.getAddressType())
										.build())
				);

		userRepository.save(user);

		log.info("User has save!");

		return user.getId();
	}

	@Override
	public void updateUser(long userId, UserDTO request) {
		User user = getUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        if (!request.getEmail().equals(user.getEmail())) {
            // check email from database if not exist then allow update email otherwise throw exception
            user.setEmail(request.getEmail());
        }
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setStatus(request.getStatus());
        user.setType(request.getType());
        user.setAddresses(convertToAddress(request.getAddresses()));
        userRepository.save(user);

        log.info("User updated successfully");
	}

	@Override
	public void changeStatus(long userId, UserStatus status) {
		User user = getUserById(userId);
        user.setStatus(status);
        userRepository.save(user);
        log.info("status changed");
	}

	@Override
	public void deleteUser(long userId) {
		getUserById(userId);
		userRepository.deleteById(userId);
	}

	@Override
	public UserDetailResponse getUser(long userId) {
		User user = getUserById(userId);
        return UserDetailResponse.builder()
                .id(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
	}
	
	// phân trang với 1 field duy nhất
	@Override
	public PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy) {
		
		// phân trang bình thường => http://localhost:8080/user/list?pageNo=0&pageSize=20
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		// phân trang 3 tham số với sort
		// sortBy chính là field (ko phải column) trong entity mình muốn sort
		// phân với 1 field
		// http://localhost:8080/user/list?pageNo=0&pageSize=20&sortBy=id
//		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		
		
		// phân trang với sort tùy chỉnh và 1 field
		// http://localhost:8080/user/list?pageNo=0&pageSize=20&sortBy=id:desc hoặc asc
		List<Sort.Order> sorts = new ArrayList<>();
		// kiểm tra nếu sortBy có giá trị
		if(StringUtils.hasLength(sortBy)) {
			// firstName:asc|desc => regex sẽ chia ra thành 3 group firstName - : - asc|desc
			Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
			Matcher matcher = pattern.matcher(sortBy);
			if(matcher.find()) {
				if(matcher.group(3).equalsIgnoreCase("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
				} else {
					sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
				}
				
			}
		}
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));
		
		Page<User> users= userRepository.findAll(pageable);
		
		List<UserDetailResponse> response = users.stream().map(user -> UserDetailResponse.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.build()).toList();
		
		return PageResponse.builder()
				.pageNo(++pageNo)
				.pageSize(pageSize)
				.totalPage(users.getTotalPages())
				.items(response)
				.build(); 
	}
	
	// phân trang với sort nhiều tiêu chí
	@Override
	public PageResponse<?> getAllUsersWithSortByMutipleColumns(int pageNo, int pageSize, String... sorts) {
		
		if(pageNo > 0) {
			pageNo -= 1;
		}
		
		List<Sort.Order> orders = new ArrayList<>();
		
		for(String sortBy: sorts) {
			Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
			Matcher matcher = pattern.matcher(sortBy);
			if(matcher.find()) {
				if(matcher.group(3).equalsIgnoreCase("asc")) {
					orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
				} else {
					orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
				}
							
			}
		}		
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));
		
		Page<User> users= userRepository.findAll(pageable);
		
		List<UserDetailResponse> response = users.stream().map(user -> UserDetailResponse.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.build()).toList();
		
		return PageResponse.builder()
				.pageNo(++pageNo)
				.pageSize(pageSize)
				.totalPage(users.getTotalPages())
				.items(response)
				.build(); 
	}
	
	// hàm này được tái sử dụng nhiều lần
	private User getUserById(long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	private Set<Address> convertToAddress(Set<AddressDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build())
        );
        return result;
    }



}
