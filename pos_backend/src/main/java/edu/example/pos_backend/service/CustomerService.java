package edu.example.pos_backend.service;

import edu.example.pos_backend.dto.CustomerDTO;
import edu.example.pos_backend.util.APIResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    public ResponseEntity<APIResponse<String>> saveCustomer(CustomerDTO customerDTO);
    public void updateCustomer(CustomerDTO customerDTO);
    public void deleteCustomer(long customerId);
    public List<CustomerDTO> getAllCustomer();
}
