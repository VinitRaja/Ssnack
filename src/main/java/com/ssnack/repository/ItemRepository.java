package com.ssnack.repository;

import com.ssnack.model.Item;
import java.util.*;

public class ItemRepository implements JpaRepositoty<Item, String> {
  public List <Item> findAllForUser(List<Item> items){
    return new ArrayList<>(items);
  }
  public Optional <Item> findByIdForUser(List<Item> items, String id){
    return items.stream().filter(i -> i.getId().equals(id)).findFirst();
  }
  public List<Item> findAll(List<Item> items){
    return new ArrayList<>(items);
  }
  public Optional <Item> findById(List<Item> items, String id){
    return items.stream().filter(i -> Objects.equals(i.getId(), id)).findFirst();
  }
  public Item save(List<Item> items, Item entity){
    deleteById(items, entity.getId());
    items.add(entity);
    return entity;
  }
  public List<Item> saveAll(List<Item> items, Iterable<Item> entities){
    for(Item e : entities){
      save(items, e);
    }
    return new ArrayList<>(items);
  }
  public boolean existsById(List<Item> items, String id){
    return items.stream().anyMatch(i->Objects.equals(i.getId, id));
  }
  public long count (List<Item> items){
    return items.size();
  }
  public void deleteById(List<Item>items, String id){
    items.removeIf(i -> Objects.equals(i.getId(), id));
  }
  public void delete (List<Item> items, Item entity){
    items.removeIf(i -> Objects.equals(i.getId(), entity.getId()));
  }
  public void deleteAll(List<Item> items, Iterable<Item> entities){
    Set<String> ids = new HashSet<>();
    for(Item e: entities){
      ids.add(e.getId());
    }
    items.removeIf(i -> ids.contains(i.getId()));
  }
  public Item addForUser(List<Item> items, Item item){
    items.add(item);
    return item;
  }
  public boolean deleteForUser(List<Item> items, String id){
    return items.removeIf(i -> i.getId().equals(id));
  }
}
