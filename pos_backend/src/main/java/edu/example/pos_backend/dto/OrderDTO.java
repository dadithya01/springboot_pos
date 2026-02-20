package edu.example.pos_backend.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private Integer customerId;
    private double total;
    private List<OrderDetailDTO> items;
}
