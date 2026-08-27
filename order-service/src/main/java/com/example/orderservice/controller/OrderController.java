package com.example.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.orderservice.client.PaymentClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.exception.UserServiceUnavailableException;
import com.example.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentClient paymentClient;
    private final UserClient userClient;

  

    public OrderController(OrderService orderService, PaymentClient paymentClient, UserClient userClient) {
        this.orderService = orderService;
		this.paymentClient = paymentClient;
		this.userClient = userClient;
    }
 
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {

        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body("Order ID must be greater than 0");
        }

        try {

            OrderResponse response =
                    orderService.getOrderById(id);

            if (response == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(response);

        } catch (UserNotFoundException e) {

            return ResponseEntity.status(502)
                    .body("Order exists, but the related user was not found");

        } catch (UserServiceUnavailableException	 e) {

            return ResponseEntity.status(503)
                    .body("User Service is currently unavailable");
        }
    }
    
    @GetMapping("/payment-test")
    public ResponseEntity<String> paymentTest(
            @RequestParam(defaultValue = "normal") String mode) {

        return ResponseEntity.ok(
                paymentClient.processPayment(mode)
        );
    }
    
//    @GetMapping("/{orderId}")
//    public String getOrder(@PathVariable Long orderId) {
//
//        UserResponse user = userClient.getUser(1L);
//
//        return "Order " + orderId
//                + " belongs to " + user.getName();
//    }
}