package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.repo.CartRepository;
import com.ecommerce.repo.OrderRepository;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CartRepository cartRepo;

    @PostMapping("/checkout/{userId}")
    public String checkout(@PathVariable Long userId) {

        List<CartItem> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            return "Cart is empty";
        }

        double total = cartItems.size() * 1000; // temp logic

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        orderRepo.save(order);

        cartRepo.deleteAll(cartItems); // clear cart

        return "Order Placed Successfully";
    }

    @GetMapping("/{userId}")
    public List<Order> myOrders(@PathVariable Long userId) {
        return orderRepo.findByUserId(userId);
    }
    @PutMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("CANCELLED");
        orderRepo.save(order);

        return "Order cancelled successfully";
    }

}

