package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Payment;
import com.ecommerce.repo.CartRepository;
import com.ecommerce.repo.OrderRepository;
import com.ecommerce.repo.PaymentRepository;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:8081")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private OrderRepository orderRepo;

    // ✅ MAKE PAYMENT + PLACE ORDER
    @PostMapping("/pay")
    public String makePayment(@RequestBody Payment payment) {

        // 1️⃣ Validate userId
        if (payment.getUserId() == null) {
            return "Invalid user";
        }

        // 2️⃣ Fetch cart items
        List<CartItem> cartItems = cartRepo.findByUserId(payment.getUserId());

        if (cartItems == null || cartItems.isEmpty()) {
            return "Cart is empty. Payment cancelled";
        }

        // 3️⃣ Calculate total amount
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getQuantity() * 1000; // mock price
        }

        // 4️⃣ Save payment
        payment.setAmount(totalAmount);
        payment.setStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepo.save(payment);

        // 5️⃣ Create order
        Order order = new Order();
        order.setUserId(payment.getUserId());
        order.setTotalAmount(totalAmount);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        orderRepo.save(order);

        // 6️⃣ Clear cart
        cartRepo.deleteAll(cartItems);

        return "Payment Successful & Order Placed";
    }

    // ✅ PAYMENT HISTORY
    @GetMapping("/history/{userId}")
    public List<Payment> paymentHistory(@PathVariable Long userId) {
        return paymentRepo.findByUserId(userId);
    }
}
