package edu.example.pos_backend.service;

import edu.example.pos_backend.dto.OrderDTO;

public interface OrderService {
    public void placeOrder(OrderDTO orderDTO);
}
