package com.tay.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tay.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "select * from User u inner join Address a on u.id = a.userId where a.city=:city")
	List<User> getAllUser(String city);

	// -- DISTINCT --
	// @Query(value = "select distinct from User u where u.firstName=:firstName and
	// u.lastName=:lastName")
	List<User> findDistinctByFirstNameAndLastName(String firstName, String lastName);

	// -- SINGLE FIELD --
	// @Query(value = "select * from User u where u.email =?1")
	List<User> findByEmail(String email);

	// -- OR --
	// @Query (value = "select * from User u where u.firstName=:name or
	// u.lastName=:name")
	List<User> findByLastNameOrFirstName(String name);

	// -- IS, EQUALS --
	// @Query(value = "select * from User u where u.firstName=:name")
	List<User> findByFirstName(String firstName);

	List<User> findByFirstNameIs(String name);

	List<User> findByFirstNameEquals(String name);

	// -- BETWEEN --
	// @Query (value = "select * from User u where u.createdAt beetween ?1 and ?2")
	List<User> findByCreatedAtBetween(Date startDate, Date endDate);

	// -- LESS THAN, GREATER THAN --
	// @Query (value = "select * from User u where u.age <: age")
	List<User> findByAgeIsLessThan(int age);

	List<User> findByAgeIsLessThanEquals();

	List<User> findByAgeIsGreaterThan();

	List<User> findByAgeIsGreaterThanEquals();

	// -- BEFORE, AFTER --
	// @Query (value = "select * from User u where u.createdAt <: date")
	List<User> findByCreatedAtBefore(Date date);

	// -- IS NULL, NULL --
	// @Query (value = "select * from User u where u.age is null")
	List<User> findByAgeIsIsNull();

	// -- IS NOT NULL, NOT NULL --
	// @Query (value = "select * from User u where u.age is not null")
	List<User> findByAgeIsIsNotNull();

	// -- LIKE, NOT LIKE -- => JPA làm theo equal nên phải tự thêm % vô
	List<User> findByLastNameLike(String lastName);

	// -- StartingWith, EndingWith --
	// @Query (value = "select * from User u where u.lastName like :lastName%")
	List<User> findByLastNameStartingWith(String lastName);

	// -- Containing -- => tương tự với toán tử like

	// -- Not -- => phép không bằng <>
	// @Query (value = "select * from User u where u.lastName <> name")
	List<User> findByLastNameNot(String name);

	// -- IN, NOT IN --
	// @Query (value = "select * from User u where u.age in (ages)")
	List<User> findByAgeIn(Collection<Integer> ages);

	// @Query (value = "select * from User u where u.age not in (ages)")
	List<User> findByAgeNotIn(Collection<Integer> ages);
	
	// --TRUE/FALSE-- => dùng cho các trường Boolean
	List<User> findByActivatedTrue();
	List<User> findByActivateFalse();
	List<User> findByActivated(Boolean activated);
	
	// -- IGNORECASE --
	List<User> findByFirstNameIgnoreCase(String name);
	List<User>  findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
	
	// -- ORDER BY --
	List<User> findByFirstNameOrderByCreatedAtDesc(String name);

	// Mối thù đi phỏng vấn
	// @Query("SELECT u FROM User u ORDER BY u.createdDate DESC")
	Optional<User> findTopByOrderByCreatedDateDesc();

}
