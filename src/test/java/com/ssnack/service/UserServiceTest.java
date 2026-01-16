package com.ssnack.service;

import com.ssnack.model.Item;
import com.ssnack.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest{
  private UserService service;

  @BeforeEach
  void setup() {
    service = new UserService();
  }
  @Test
  void createUser_and_retrieve() {
    User u = service.createUser("Alice");
    assertNotNull(u.getId());
    assertEquals("Alice", u.getName());
    assertTrue(service.getUser(u.getId()).isPresent());
  }
  @Test
  void addItems_upToMax_thenFail() {
    User u = service.createUser("Bob");
    for (int i=0; i<10; i++){
      assertDoesNotThrow(() -> service.addItem(u.getId(), "Items " + i));
    }
    IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.addItem(u.getId(), "Overflow"));
    assertTrue(ex.getMessage().contains("maximum"));
  }
  @Test
  void removeItem_success() {
    User u = service.createUser("Carl");
    var item = service.addItem(u.getId(), "Snack");
    assertTrue(service.removeItem(u.getId(), item.getId()));
  }
  @Test
  void sellItem_moveItem_fromSellerToBuyer(){
    
  }
}
