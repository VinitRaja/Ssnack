package com.ssnack.controller;

import com.ssnack.model.Item;
import com.ssnack.model.User;
import com.ssnack.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController{
  private final UserService userService;
  public UserController(UserService userService){
    this.userService = userService;
  }
  @GetMapping
  public List<User> listUsers(){
    return userService.listUsers();
  }
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request){
    User user = userService.createUser(request.name());
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable String id){
    return userService.getUser(id)
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
  }
  @GetMapping("/{id}/by-name")
  public ResponseEntity <User> getUserByIdAndName(@PathVariable String id, @RequestParam("name") String name){
    return userService.getUser(id, name)
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id){
    boolean deleted = userService.deleteUser(id);
    return deleted ? ResponseEntity.noContent().build : ResponseEntity.notFound().build();
  }
  @PostMapping("/{userId}/items")
  public ResponseEntity<?> addItem(@PathVariable String userId, @RequestBody CreateItemRequest req){
    try{
      Item item = userService.addItem(userId, req.name());
      return new ResponseEntity<>(item, HttpStatus.CREATED);
    } catch (IllegalStateException e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
  @GetMapping("/{userId}/items")
  public ResponseEntity<List<Item>> listItems(@PathVariable String userId){
    try{
      return ResponseEntity.ok(userService.listItems(userId));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
  @DeleteMapping("/{userId}/items/{itemId}")
  public ResponseEntity<Void> removeItem(@PathVariable String userId, @PathVariable String itemId){
    try {
      boolean removed = userService.removeItem(userId, itemId);
      return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
  @PostMapping("/{sellerId}/sell/{itemId}/to/{buyerId}")
  public ResponseEntity<Item> sellItem(@PathVariable String sellerId, @PathVariable String itemId, @PathVariable String buyerId){
    try{
      Item item = userService.sellItem(sellerId, itemId, buyerId);
      return new ResponseEntity<>(item, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }
  @PostMapping("/{lenderId}/borrow/{itemId}/to/{borrowerId}")
  public ResponseEntity<Item> borrowItem(@PathVariable String lenderId, @PathVariable String itemId, @PathVariable String borrowerId){
    try{
      Item item = userService.borrowItem(lenderId, itemId, borrowerId);
      return new ResponseEntity<>(item, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not Available & quantity is" + null);
    }
  }
  @PostMapping("/{borrowerId}/return/{borrowItemId}")
  public ResponseEntity<Void> returnBorrowItem (@PathVariable String borrowerId, @PathVariable String borrowedItemId){
    try{
      bolean ok = userService.returnBorrowedItem(borrowerId, borrowedItemId);
      return ok ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
  @PostMapping("/{borrowerId}/sell-borrowed/{borrowedItemId}/to/{buyerId}")
  public ResponseEntity<Item> sellBorrowedItem(@PathVariable String borrowerId, @PathVariable String borrowedItemId, @PathVariable String buyerId){
    try{
      Item item = userService.sellBorrowedItem(borrowerId, borrowedItemId, buyerId);
      return new ResponseEntity<>(item, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not Available" + null);
    }
  }
  public record CreateUserRequest(String name){}
  public record CreateItemRequest(String name){}
}
