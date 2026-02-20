package edu.example.pos_backend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {
    
    @Nullable
    private Integer id;
    @NotBlank(message = "Item name is mandatory")
    @Size(min = 3, max = 50, message = "Item name must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9 ]+$",
            message = "Item name can contain only letters, numbers and spaces"
    )
    @NotBlank
    private String name;


    @Min(value = 0, message = "Quantity cannot be negative")
    @NotNull
    private Integer qty;

    @Positive(message = "Price must be greater than 0")
    @NotNull @PositiveOrZero
    private double price;






}
