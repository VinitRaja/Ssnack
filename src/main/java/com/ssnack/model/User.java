package com.ssnack.model;

import java.util.*;

public class User{

  private final String id;
  private String name;
  private final List<Item> items = new ArrayList<>();

  public User(String id, String name) {
        this.id = id;
        this.name = name;
  }
  public User() {
    }

  public String getId() {
    return id;
  }

// getter for a mutable field
public String getName() {
    return name;
  }
  public void setName(String name){
    this.name = name;
  }

// Getter for a List
public List<Item> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode(){
    return Objects.hash(id);
  }
}
