package edu.example.pos_backend.service;

import edu.example.pos_backend.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    public void saveCustomer(CustomerDTO customerDTO);
    public void updateCustomer(CustomerDTO customerDTO);
    public void deleteCustomer(long customerId);
    public List<CustomerDTO> getAllCustomer();
}
