package edu.example.pos_backend.service.impl;

import edu.example.pos_backend.dto.CustomerDTO;
import edu.example.pos_backend.entity.Customer;
import edu.example.pos_backend.exception.BadRequestException;
import edu.example.pos_backend.exception.ResourceNotFoundException;
import edu.example.pos_backend.respository.CustomerRepository;
import edu.example.pos_backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new BadRequestException("Request body is missing");
        }
        customerRepository.save(modelMapper.map(customerDTO, Customer.class));
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new BadRequestException("Request body is missing"); // 400
        }
        if (customerDTO.getCId() == null) {
            throw new BadRequestException("Customer ID is required for update"); // 400
        }

        Customer existing = customerRepository.findById(customerDTO.getCId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found")); // 404

        existing.setCName(customerDTO.getCName());
        existing.setCAddress(customerDTO.getCAddress());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll(); // never null
        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {}.getType());
    }

    @Override
    public CustomerDTO getCustomer(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found")); // 404
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public void deleteCustomer(int id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found"); // 404
        }
        customerRepository.deleteById(id);
    }
}
