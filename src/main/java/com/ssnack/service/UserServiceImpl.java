package com.ssnack.service;

import com.ssnack.model.Item;
import com.ssnack.model.User;
import com.ssnack.repository.ItemRepository;
import com.ssnack.repository.UserRepository;
import java.Util.List;
import java.Util.Optional;

public interface UserServiceImpl{
  int MAX_ITEMS = 10;
  UserRepository getUserRepository();
  ItemRepository getItemRepository();

  default List <User> listUsers(){
    return getUserRepository().findAll();
  }
  default Optional<User> getUser(String id){
    return getUserRepository().findById(id);
  }
  default Optional<User> getUser(String id, String name){
    return getUserRepository().findById(id).filter(u -> u.getName() != null && u.getName().equals(name));
  }
  default User createUser(String name){
    User user = new User(name);
    return getUserRepository().save(user);
  }
  default boolean deleteUser(String id){
    return getUserRepository().deleteById(id);
  }
  default List <Item> listItems(String userId){
    User user = getUserRepository().findById(userId).orElseThrow(() -> new IllegalArgumentException("User not Found"));
    return getItemRepository().findAllForUser(user.getItems());
  }
  default Item addItem(String userId, String itemName){
    User user = getUserRepository().findById(userId).orElseThrow(() -> new IllegalArgumentException("User not Found"));
    if (user.getItems().size() > MAX_ITEMS){
      throw new IllegalStateException("User already has maximum of " + MAX_ITEMS + " items"));
    }
    Item item = new Item(itemName);
    return getItemRepository().addForUser(user.getItems(), item);
  }
  default boolean removeItem(String userId, String itemId){
    User user = getUserRepository().findById(userId).orElseThrow(() -> new IllegalArgumentException("User not Found"));
    return getItemRepository().deleteForUser(user.getItems(), itemId);
  }
  default Item sellItem(String sellerUserId, String itemId, String buyerUserId){
    User seller = getUserRepository().findById(sellerUserId).orElseThrow(() -> new IllegalArgumentException("Seller not Found"));
    User buyer = getUserRepository().findById(buyerUserId).orElseThrow(() -> new IllegalArgumentException("Buyer not Found"));
    if (buyer.getItems().size() >= MAX_ITEMS){
      throw new IllegalStateException("Buyer already has maximum of " + MAX_ITEMS + " items"));
    }
    Item item = getItemRepository().findByIdForUser(seller.getItems(), itemId).orElseThrow(() -> new IllegalArgumentException("Items not Found"));
    boolean removed = getItemRepository().deleteForUser(seller.getItems(), itemId);
    if (!removed){
      throw new IllegalStateException("Failed to remove item from seller, Please try again or item already not exits! ");
    }
    item.setBorrowed(false);
    item.setBorrowedFromUserId(null);
    item.setSourceItemId(null);
    return getItemRepository().addForUser(buyer.getItems(), item);
  }
  default Item borrowItem(String lenderUserId, String itemId, String borrowerUserId){
    User lender = getUserRepository().findById(lenderUserId).orElseThrow(() -> new IllegalArgumentException("Lender not Found"));
    User borrower = getUserRepository().findById(borrowerUserId).orElseThrow(() -> new IllegalArgumentException("Borrower not Found"));
    if (borrower.getItems().size() >= MAX_ITEMS){
      throw new IllegalStateException("borrower already has maximum of " + MAX_ITEMS + " items"));
    }
    Item lenderItem = getItemRepository().findByIdForUser(lender.getItems(), itemId).orElseThrow(() -> new IllegalArgumentException("Items not Found for lender"));
    Item borrowedVopy = new Item(lenderItem.getName());
    borrowedCopy.setBorrowed(true);
    borrowedCopy.setBorrowedFromUserId(lender.getId());
    borrowedCopy.setSourceItemId(lenderItem.getId());
    return getItemRepository().addForUser(borrower.getItems(), borrowedCopy);
  }
  default boolean returnBorrowedItem(String borrowerUserId, String borrowedItemId){
    User borrower = getUserRepository().findById(borrowerUserId).orElseThrow(() -> new IllegalArgumentException("Lender not Found"));
    User borrowedItem = getItemRepository().findByIdForUser(borrower.getItems(), borrowedItemId).orElseThrow(() -> new IllegalArgumentException("Borrowed Items not Found"));
    if (!borrowedItem.isBorrowed()){
      throw new IllegalStateException("Item is not marked as borrowed");
    }
    return getItemRepository().deleteForUser(borrower.getItems(), borrowedItemId);
  }
  default Item sellBorrowedItem(String borrowerUserId, String borrowedItemId, String buyerUserId){
    User borrower = getUserRepository().findById(borrowerUserId).orElseThrow(() -> new IllegalArgumentException("Lender not Found"));
    User buyer = getUserRepository().findById(buyerUserId).orElseThrow(() -> new IllegalArgumentException("Lender not Found"));
    if (buyer.getItems().size() >= MAX_ITEMS){
      throw new IllegalStateException("borrower already has maximum of " + MAX_ITEMS + " items"));
    }
    Item borrowedItem = getItemRepository().findByIdForUser(borrower.getItems(), borrowedItemId).orElseThrow(() -> new IllegalArgumentException("Borrowed Items not Found"));
    if (!borrowedItem.isBorrowed()){
      throw new IllegalStateException("Item is not marked as borrowed");
    }
    boolean removed = getItemRepository().deleteForUser(borrower.getItems(), borrowedItemId);
    if(!removed){
      throw new IllegalStateException("Failed to remove borrowed item from borrower");
    }
    borrowedItem.setBorrowed(false);
    borrowedItem.setBorrowedFromUserId(null);
    borrowedItem.setSourceItemId(null);
    return getItemRepository().addForUser(buyer.getItems(), borrowedItem);
  }
}
