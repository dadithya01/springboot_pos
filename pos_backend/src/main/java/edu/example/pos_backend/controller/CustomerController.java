package edu.example.pos_backend.controller;

import edu.example.pos_backend.dto.CustomerDTO;
import edu.example.pos_backend.service.CustomerService;
import edu.example.pos_backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/customer")
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<APIResponse<String>> saveCustomer(
            @RequestBody @Valid CustomerDTO customerDTO) {

        customerService.saveCustomer(customerDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        201,
                        "Customer saved successfully",
                        null
                ));
    }


    @PutMapping
    public ResponseEntity<APIResponse<String>> updateCustomer(
            @RequestBody @Valid CustomerDTO customerDTO) {

        customerService.updateCustomer(customerDTO);

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Customer updated successfully",
                        null
                )
        );
    }


    @GetMapping
    public ResponseEntity<APIResponse<List<CustomerDTO>>> getAllCustomers() {

        List<CustomerDTO> customers = customerService.getAllCustomers();

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Customer list fetched",
                        customers
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CustomerDTO>> getCustomer(
            @PathVariable int id) {

        CustomerDTO customer = customerService.getCustomer(id);

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Customer fetched",
                        customer
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCustomer(
            @PathVariable int id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Customer deleted successfully",
                        null
                )
        );
    }
}
