package edu.example.pos_backend.service.impl;

import edu.example.pos_backend.dto.CustomerDTO;
import edu.example.pos_backend.entity.Customer;
import edu.example.pos_backend.repository.CustomerRepo;
import edu.example.pos_backend.service.CustomerService;
import edu.example.pos_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<APIResponse<String>> saveCustomer(CustomerDTO customerDTO) {
        customerRepo.save(modelMapper.map(customerDTO, Customer.class));

        return new ResponseEntity<>(new APIResponse<>(500,"internal server error",null), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        customerRepo.save(modelMapper.map(customerDTO, Customer.class));
    }

    @Override
    public void deleteCustomer(long customerId) {
        customerRepo.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customers = customerRepo.findAll();
        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {}.getType());
    }
}
