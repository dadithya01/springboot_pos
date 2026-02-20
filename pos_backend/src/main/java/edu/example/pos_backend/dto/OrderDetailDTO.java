package edu.example.pos_backend.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDetailDTO {
    private Integer itemId;
    private int qty;
    private double unitPrice;
}
