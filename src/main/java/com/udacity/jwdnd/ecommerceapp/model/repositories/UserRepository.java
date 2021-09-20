package com.udacity.jwdnd.ecommerceapp.model.repositories;

import com.udacity.jwdnd.ecommerceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
