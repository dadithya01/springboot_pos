package edu.example.pos_backend.service;

import edu.example.pos_backend.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    void saveCustomer(CustomerDTO dto);
    void updateCustomer(CustomerDTO dto);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomer(int id);
    void deleteCustomer(int id);

}
