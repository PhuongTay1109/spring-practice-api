package com.tay.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.tay.dto.response.PageResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

// làm việc với JPA và Hibernate
@Repository
public class SearchRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	PageResponse<?> getAllUsersWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
		
		// query ra list user
		StringBuilder sqlQuery = new StringBuilder("Select u from User u where 1=1");
		
		Query selectQuery = entityManager.createQuery(sqlQuery.toString());

		selectQuery.setFirstResult(pageNo);
		selectQuery.setMaxResults(pageSize);
	
		
		
		
		// query ra số record theo các điều kiện tìm kiếm
		
		return null;		
	}
}
