package com.example.demo.repository;

import com.example.demo.domain.Item;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {


  private final EntityManager em;

  public void save(Item item) {
    em.persist(item);
  }

  public Item findById(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class)
        .getResultList();
  }

}
