package edu.example.pos_backend.service;

import edu.example.pos_backend.dto.OrderDTO;

public interface OrderService {
    Integer placeOrder(OrderDTO orderDTO);
    Integer getNextOrderId();

}
