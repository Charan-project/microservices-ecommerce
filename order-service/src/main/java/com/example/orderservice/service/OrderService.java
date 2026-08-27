package com.example.orderservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.model.Order;

@Service
public class OrderService {

    private final UserClient userClient;

    private final Map<Long, Order> orders = new HashMap<>();

    public OrderService(UserClient userClient) {

        this.userClient = userClient;

        orders.put(1001L, new Order(1001L, 1L));
        orders.put(1002L, new Order(1002L, 2L));
        orders.put(1003L, new Order(1003L, 3L));
        orders.put(1004L, new Order(1004L, 99L));
    }

    public OrderResponse getOrderById(Long orderId) {

        Order order = orders.get(orderId);

        if (order == null) {
            return null;
        }

        UserResponse user =
                userClient.getUserById(order.getUserId());
        

        return new OrderResponse(
                order.getOrderId(),
                order.getUserId(),
                user
        );
    }
}