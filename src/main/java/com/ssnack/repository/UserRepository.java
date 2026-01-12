package com.ssnack.repository;

import com.ssnack.model.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements JpaRepository<User, String>{
  private final Map<String, User> store = new ConcurrentHashMap<>();
  public List <User> findAll(){
    return new ArrayList<>(store.values());
  }
  public Optional<User> findById(String id){
    return Optional.ofNullable(store.get(id));
  }
  public User save(User user){
    store.put(user.getId(), user);
    return user;
  }
  public boolean deleteById(String id){
    return store.remove(id) != null;
  }
}
