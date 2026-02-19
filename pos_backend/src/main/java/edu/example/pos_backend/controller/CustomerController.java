package edu.example.pos_backend.controller;

import edu.example.pos_backend.dto.CustomerDTO;
import edu.example.pos_backend.service.impl.CustomerServiceImpl;
import edu.example.pos_backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/customers")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@Validated
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        System.out.println(customerDTO);
        customerServiceImpl.saveCustomer(customerDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "Customer saved successfully", null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity <APIResponse<String>> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        customerServiceImpl.updateCustomer(customerDTO);
        return new ResponseEntity<>(new APIResponse<>(200, "Customer updated successfully", null), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity <APIResponse<String>> deleteCustomer(@PathVariable long customerId) {
        customerServiceImpl.deleteCustomer(customerId);
        return new ResponseEntity<>(new APIResponse<>(200, "Customer deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity <APIResponse<List<CustomerDTO>>> getAllCustomers() {
        List<CustomerDTO> customers = customerServiceImpl.getAllCustomer();
        return new ResponseEntity<>(new APIResponse<>(200, "Customers retrieved successfully", customers), HttpStatus.OK);
    }
}
