package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.domain.Address;
import com.example.demo.domain.Book;
import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.exception.NotEnoughStockException;
import com.example.demo.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Test
  void shouldPlaceOrderSuccessfullyWhenStockIsSufficient() {
    // Given
    Member member = createMember();
    Item item = createBook("foo", 10000, 10);
    int orderCount = 2;

    // When
    Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

    // Then
    Order order = orderRepository.findById(orderId);

    assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    assertThat(order.getOrderItems()).hasSize(1);
    assertThat(order.getTotalPrice()).isEqualTo(item.getPrice() * orderCount);
    assertThat(item.getStockQuantity()).isEqualTo(8);
  }

  @Test
  void shouldThrowExceptionWhenOrderQuantityExceedsStock() {
    // Given
    Member member = createMember();
    Item item = createBook("foo", 10000, 10);
    int orderCount = 11; // 재고보다 많은 수량

    // When & Then
    assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
        .isInstanceOf(NotEnoughStockException.class);
  }

  @Test
  void test() {
    // Given
    Member member = createMember();
    Item item = createBook("foo", 10000, 10);
    int orderCount = 2;
    Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

    // When
    orderService.cancelOrder(orderId);

    // Then
    Order canceledOrder = orderRepository.findById(orderId);
    assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    assertThat(item.getStockQuantity()).isEqualTo(10);
  }

  private Member createMember() {
    Member member = new Member();
    member.setUsername("foo");
    member.setAddress(new Address("bar", "baz", "qux"));
    em.persist(member);
    return member;
  }

  private Book createBook(String name, int price, int stockQuantity) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    em.persist(book);
    return book;
  }

}