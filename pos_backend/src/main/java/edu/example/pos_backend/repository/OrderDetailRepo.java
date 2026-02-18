package edu.example.pos_backend.repository;

import edu.example.pos_backend.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,Long> {
}
