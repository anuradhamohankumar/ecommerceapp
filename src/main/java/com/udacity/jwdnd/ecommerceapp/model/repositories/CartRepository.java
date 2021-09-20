package com.udacity.jwdnd.ecommerceapp.model.repositories;

import com.udacity.jwdnd.ecommerceapp.model.Cart;
import com.udacity.jwdnd.ecommerceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
