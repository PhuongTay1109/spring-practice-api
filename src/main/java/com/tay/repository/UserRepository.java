package com.tay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tay.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
