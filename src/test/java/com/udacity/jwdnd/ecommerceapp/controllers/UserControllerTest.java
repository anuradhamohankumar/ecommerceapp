package com.udacity.jwdnd.ecommerceapp.controllers;

import com.udacity.jwdnd.ecommerceapp.TestUtils;
import com.udacity.jwdnd.ecommerceapp.model.Cart;
import com.udacity.jwdnd.ecommerceapp.model.User;
import com.udacity.jwdnd.ecommerceapp.model.repositories.CartRepository;
import com.udacity.jwdnd.ecommerceapp.model.repositories.UserRepository;
import com.udacity.jwdnd.ecommerceapp.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserController.class)
public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController=new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepo);
        TestUtils.injectObjects(userController,"cartRepository",cartRepo);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",encoder);
        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("test");
        user.setPassword("thisIsHashed");
        when(encoder.encode("test1234")).thenReturn("thisIsHashed");
        when(userRepo.findByUsername("test")).thenReturn(user);
        when(userRepo.findById(0L)).thenReturn(java.util.Optional.of(user));
    }

    @Test
    public void create_user() throws Exception {

        CreateUserRequest  r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("test1234");
        r.setConfirmPassword("test1234");

        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0,u.getId());
        assertEquals("test",u.getUsername());
        assertEquals("thisIsHashed",u.getPassword());
    }

    @Test
    public void findUserByUserNameNotFound(){
        ResponseEntity<User> userResponseEntity = userController.findByUserName("medo");
        assertNotNull(userResponseEntity);
        assertEquals(404, userResponseEntity.getStatusCodeValue());
    }


    @Test
    public void findUserByUsername(){
        ResponseEntity<User> userResponseEntity = userController.findByUserName("test");
        assertNotNull(userResponseEntity);
        assertEquals(200, userResponseEntity.getStatusCodeValue());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals("test", user.getUsername());
        assertEquals("thisIsHashed",user.getPassword());
    }

    @Test
    public void findUserById(){
        ResponseEntity<User> userResponseEntity = userController.findById(0L);
        assertNotNull(userResponseEntity);
        assertEquals(200, userResponseEntity.getStatusCodeValue());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals("test", user.getUsername());
    }
    @Test
    public void findUserByIdNotFound(){
        ResponseEntity<User> userResponseEntity = userController.findById(999L);
        assertNotNull(userResponseEntity);
        assertEquals(404, userResponseEntity.getStatusCodeValue());
    }
}
