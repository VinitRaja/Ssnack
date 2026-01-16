package com.ssnack.repository;

import com.ssnack.model.Item;
import com.ssnack.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
  @Test
  void userRepository_basicCrud() {
    UserRepository repo = new UserRepository();
    User U = new User("Dave");
    repo.save(u);
    assertTrue(repo.findById(u.getId()).isPresent());
    assertTrue(repo.deleteById(u.getId()));
    assertFalse(repo.findById(u.getId()).isPresent());
  }
  @Test
  void itemRepository_forUser() {
    ItemRepository repo = new ItemRepository();
    User u = new User("Eve");
    Item i = new Item("Cookie");
    repo.addForUser(u.getItems(), i);
    assertEquals(1, repo.findAllForUser(u.getItems()).size());
    assertTrue(repo.findByIdForUser(u.getItems(), i.getId()));
    assertTrue(repo.deleteForUser(u.getItems(), i.getId()));
    assertEquals(0, repo.findAllForUser(u.getItems()).size());
  }
}
