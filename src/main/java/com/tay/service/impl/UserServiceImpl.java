package com.tay.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tay.dto.request.AddressDTO;
import com.tay.dto.request.UserDTO;
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
