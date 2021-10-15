package com.shop.shop.service;

import com.shop.shop.dto.OrderDto;
import com.shop.shop.entity.Item;
import com.shop.shop.entity.Member;
import com.shop.shop.entity.Order;
import com.shop.shop.entity.OrderItem;
import com.shop.shop.repository.ItemRepository;
import com.shop.shop.repository.MemberRepository;
import com.shop.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
