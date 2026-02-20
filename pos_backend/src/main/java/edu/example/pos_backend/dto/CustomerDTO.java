package edu.example.pos_backend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    @Nullable
    private Integer cId;

    @NotBlank(message = "customer name is mandatory")
    @Size(min = 3, max = 50, message = "customer name must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "customer name must contain only letters and spaces"
    )
    private String cName;

    @NotBlank(message = "customer address is mandatory")
    @Size(min = 10, max = 100, message = "address must be between 10 and 100 characters")
    private String cAddress;
}
