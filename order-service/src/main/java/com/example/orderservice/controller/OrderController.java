package com.example.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.exception.UserNotFoundException;
import com.example.orderservice.exception.UserServiceUnavailableException;
import com.example.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getOrderById(
//            @PathVariable Long id) {
//
//        if (id == null || id <= 0) {
//            return ResponseEntity.badRequest()
//                    .body("Order ID must be greater than 0");
//        }
//
//        OrderResponse response =
//                orderService.getOrderById(id);
//
//        if (response == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (response.getUser() == null) {
//            return ResponseEntity.status(502)
//                    .body("Related user was not found in User Service");
//        }
//
//        return ResponseEntity.ok(response);
//    }
    
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
}