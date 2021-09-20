package com.udacity.jwdnd.ecommerceapp.controllers;

import com.udacity.jwdnd.ecommerceapp.TestUtils;
import com.udacity.jwdnd.ecommerceapp.model.Cart;
import com.udacity.jwdnd.ecommerceapp.model.Item;
import com.udacity.jwdnd.ecommerceapp.model.User;
import com.udacity.jwdnd.ecommerceapp.model.repositories.CartRepository;
import com.udacity.jwdnd.ecommerceapp.model.repositories.ItemRepository;
import com.udacity.jwdnd.ecommerceapp.model.repositories.UserRepository;
import com.udacity.jwdnd.ecommerceapp.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes =  CartController.class)
public class CartControllerTest {
    private  CartController cartController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController=new CartController();
        TestUtils.injectObjects(cartController,"itemRepository",itemRepo);
        TestUtils.injectObjects(cartController,"userRepository",userRepo);
        TestUtils.injectObjects(cartController,"cartRepository",cartRepo);

        Cart cart = new Cart();
        User user = new User(1,"test","test1234",cart);
        Item item =new Item(1l,"Square Widget",BigDecimal.valueOf(1.99),"A widget that is sqaure");
        when(userRepo.findByUsername("test")).thenReturn(user);
        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item));
    }

    @Test
    public void addToCart(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername("test");
        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);
        assertNotNull(cartResponseEntity);
        assertEquals(200, cartResponseEntity.getStatusCodeValue());
        Cart cart = cartResponseEntity.getBody();
        assertNotNull(cart);
        assertEquals(BigDecimal.valueOf(1.99), cart.getTotal());
    }

    @Test
    public void removeItemFromCart() {
        // Set up test by adding two items to cart.
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(3);
        modifyCartRequest.setUsername("test");
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertNotNull(cart);
        assertEquals(BigDecimal.valueOf(5.97), cart.getTotal());
        modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername("test");
        response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        cart = response.getBody();
        assertNotNull(cart);
        assertEquals(BigDecimal.valueOf(3.98), cart.getTotal());
    }
    @Test
    public void invalidUserRemovesItems() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername("temp");
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
