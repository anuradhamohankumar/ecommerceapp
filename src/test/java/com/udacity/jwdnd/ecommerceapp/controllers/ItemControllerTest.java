package com.udacity.jwdnd.ecommerceapp.controllers;

import com.udacity.jwdnd.ecommerceapp.TestUtils;
import com.udacity.jwdnd.ecommerceapp.model.Item;
import com.udacity.jwdnd.ecommerceapp.model.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ItemController.class)
public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController=new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepo);

        Item item = new Item();
        item.setId(1l);
        item.setName("item1");
        item.setPrice(new BigDecimal(50.00));
        List<Item> items = new ArrayList<>();
        items.add(item);
        when(itemRepo.findByName("item1")).thenReturn(items);
        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item));
    }

    @Test
    public void findItemsByNameNotFound(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("medo");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    public void findItemsByname(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item1");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals("item1", items.get(0).getName());
    }

    @Test
    public void findItemById(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody();
        assertNotNull(item);
        assertEquals("item1", item.getName());
    }
    @Test
    public void findItemByIdNotFound(){
        ResponseEntity<Item> response = itemController.getItemById(999L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
