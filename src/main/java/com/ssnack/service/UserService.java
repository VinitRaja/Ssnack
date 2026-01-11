package com.ssnack.service;

import com.ssnack.repository.ItemRepository;
import com.ssnack.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImpl{
  private final UserRepository userRepository = new UserRepository();
  private final ItemRepository itemRepository = new ItemRepository();

  @Override
  public UserRepository getUserRepository(){
    return userRepository;
  }
  @Override
  public ItemRepository getItemRepository(){
    return itemRepository;
  }
}
