package com.udacity.jwdnd.ecommerceapp.controllers;

import com.udacity.jwdnd.ecommerceapp.TestUtils;
import com.udacity.jwdnd.ecommerceapp.model.Cart;
import com.udacity.jwdnd.ecommerceapp.model.Item;
import com.udacity.jwdnd.ecommerceapp.model.User;
import com.udacity.jwdnd.ecommerceapp.model.UserOrder;
import com.udacity.jwdnd.ecommerceapp.model.repositories.OrderRepository;
import com.udacity.jwdnd.ecommerceapp.model.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

@SpringBootTest(classes = OrderController.class)
public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);


    @Before
    public void setUp() {
        orderController=new OrderController();
        TestUtils.injectObjects(orderController,"userRepository",userRepo);
        TestUtils.injectObjects(orderController,"orderRepository",orderRepo);

        Cart cart = new Cart();
        Item item =new Item(1l,"Square Widget", BigDecimal.valueOf(1.99),"A widget that is sqaure");
        cart.addItem(item);
        cart.addItem(item);
        User user = new User(1,"test","test1234",cart);
        when(userRepo.findByUsername("test")).thenReturn(user);
    }

    @Test
    public void submitOrder() {
        ResponseEntity<UserOrder> response = orderController.submit("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder userOrder = response.getBody();
        assertNotNull(userOrder);
        assertEquals(2, userOrder.getItems().size());
        assertEquals(BigDecimal.valueOf(3.98), userOrder.getTotal());
    }


    @Test
    public void invalidUserSubmitOrder() {
        ResponseEntity<UserOrder> response = orderController.submit("temp");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
