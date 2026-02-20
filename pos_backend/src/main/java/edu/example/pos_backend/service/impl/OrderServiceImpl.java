package edu.example.pos_backend.service.impl;

import edu.example.pos_backend.dto.OrderDTO;
import edu.example.pos_backend.dto.OrderDetailDTO;
import edu.example.pos_backend.entity.Customer;
import edu.example.pos_backend.entity.Item;
import edu.example.pos_backend.entity.OrderDetail;
import edu.example.pos_backend.entity.Orders;
import edu.example.pos_backend.exception.BadRequestException;
import edu.example.pos_backend.exception.ResourceNotFoundException;
import edu.example.pos_backend.respository.CustomerRepository;
import edu.example.pos_backend.respository.ItemRepository;
import edu.example.pos_backend.respository.OrderDetailRepository;
import edu.example.pos_backend.respository.OrderRepository;
import edu.example.pos_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderDetailRepository orderDetailRepo;
    private final CustomerRepository customerRepo;
    private final ItemRepository itemRepo;

    @Override
    public Integer placeOrder(OrderDTO orderDTO) {

        // 1) basic validation
        if (orderDTO == null) throw new BadRequestException("Request body is missing");
        if (orderDTO.getCustomerId() == null) throw new BadRequestException("Customer ID is required");
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty())
            throw new BadRequestException("Cart is empty");

        // 2) load customer entity (must exist)
        Customer customer = customerRepo.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + orderDTO.getCustomerId()));

        // 3) create + save order header (ID is generated)
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setTotal(orderDTO.getTotal()); // (better: compute from details)
        order = orderRepo.save(order);

        // 4) create details + update stock
        for (OrderDetailDTO d : orderDTO.getItems()) {

            if (d == null) throw new BadRequestException("Order item is missing");
            if (d.getItemId() == null) throw new BadRequestException("Item ID is required in order detail");
            if (d.getQty() <= 0) throw new BadRequestException("Invalid qty for item: " + d.getItemId());
            if (d.getUnitPrice() <= 0) throw new BadRequestException("Invalid unit price for item: " + d.getItemId());

            Item item = itemRepo.findById(d.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + d.getItemId()));

            if (item.getQty() < d.getQty()) {
                throw new BadRequestException(
                        "Not enough stock for " + d.getItemId() +
                                " (available=" + item.getQty() + ", requested=" + d.getQty() + ")"
                );
            }

            // reduce stock (managed entity → auto update on commit)
            item.setQty(item.getQty() - d.getQty());

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);     // ✅ set FK via relationship
            detail.setItem(item);       // ✅ set FK via relationship
            detail.setQty(d.getQty());
            detail.setUnitPrice(d.getUnitPrice());

            orderDetailRepo.save(detail);
        }

        // any exception => rollback all ✅ because @Transactional

        return order.getOrderId();
    }

    @Override
    public Integer getNextOrderId() {
        Integer maxId = orderRepo.findMaxOrderId();
        return (maxId == null ? 1 : maxId + 1);
    }



}

