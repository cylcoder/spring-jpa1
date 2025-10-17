package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void save(Item item) {
    itemRepository.save(item);
  }

  public List<Item> findAll() {
    return itemRepository.findAll();
  }

  public Item findById(Long id) {
    return itemRepository.findById(id);
  }

}
