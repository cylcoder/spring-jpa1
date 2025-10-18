package com.example.demo.service;

import com.example.demo.domain.Delivery;
import com.example.demo.domain.DeliveryStatus;
import com.example.demo.domain.Item;
import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderSearch;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ItemRepository itemRepository;

  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    Member member = memberRepository.findById(memberId);
    Item item = itemRepository.findById(itemId);

    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());
    delivery.setStatus(DeliveryStatus.READY);

    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
    Order order = Order.createOrder(member, delivery, orderItem);

    orderRepository.save(order);
    return order.getId();
  }

  @Transactional
  public void cancelOrder(Long id) {
    Order order = orderRepository.findById(id);
    order.cancel();
  }

  public List<Order> findAll(OrderSearch orderSearch) {
    return orderRepository.findAll(orderSearch);
  }

}
