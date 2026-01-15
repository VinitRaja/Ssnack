package com.ssnack.model;

import java.util.Objects;
import java.util.UUID;

public class Item {
  private final String id;
  private String name;
  private boolean borrowed;
  private String borrowedFromUserId;
  private String sourceItemId;

  public Item(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    }

  public String getId() { return id; }
  public String getName() { return name; }
  public boolean isBorrowed() { return borrowed; }
  public String getBorrowedFromUserId() { return borrowedFromUserId; }
  public String getSourceItemId() { return sourceItemId; }

  public void setSourceItemId(String sourceItemId) {
    this.sourceItemId = sourceItemId;  }
  public void setName(String name) { this.name = name; }
  public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }
  public void setBorrowedFromUserId(String borrowedFromUserId) { this.borrowedFromUserId = borrowedFromUserId; }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Item that = (Item) o;
      return Objects.equals(id, that.id) && Objects.equals(sourceItemId, that.sourceItemId);
    }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, sourceItemId);
    }
}
