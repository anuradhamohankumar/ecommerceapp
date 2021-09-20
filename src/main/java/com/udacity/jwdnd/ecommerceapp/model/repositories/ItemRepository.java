package com.udacity.jwdnd.ecommerceapp.model.repositories;

import java.util.List;
import com.udacity.jwdnd.ecommerceapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByName(String name);

}
