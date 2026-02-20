package edu.example.pos_backend.respository;

import edu.example.pos_backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT MAX(o.orderId) FROM Orders o")
    Integer findMaxOrderId();

}
