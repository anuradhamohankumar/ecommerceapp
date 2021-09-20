package com.udacity.jwdnd.ecommerceapp.model.repositories;

import java.util.List;
import com.udacity.jwdnd.ecommerceapp.model.User;
import com.udacity.jwdnd.ecommerceapp.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
