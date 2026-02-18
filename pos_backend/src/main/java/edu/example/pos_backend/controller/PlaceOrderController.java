package edu.example.pos_backend.controller;

import edu.example.pos_backend.dto.OrderDTO;
import edu.example.pos_backend.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin
public class PlaceOrderController {

    private  final OrderServiceImpl orderServiceImpl;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO){
        orderServiceImpl.placeOrder(orderDTO);
        return ResponseEntity.ok("Order placed successfully");
    }
}
