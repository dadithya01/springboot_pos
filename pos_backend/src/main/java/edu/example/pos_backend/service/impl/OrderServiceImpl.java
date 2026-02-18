package edu.example.pos_backend.service.impl;

import edu.example.pos_backend.dto.OrderDTO;
import edu.example.pos_backend.dto.OrderDetailDTO;
import edu.example.pos_backend.entity.Customer;
import edu.example.pos_backend.entity.Item;
import edu.example.pos_backend.entity.Order;
import edu.example.pos_backend.entity.OrderDetail;
import edu.example.pos_backend.repository.CustomerRepo;
import edu.example.pos_backend.repository.ItemRepo;
import edu.example.pos_backend.repository.OrderRepo;
import edu.example.pos_backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;

    @Override
    public void placeOrder(OrderDTO orderDTO) {
        Customer customer = customerRepo.findById(Long.valueOf(orderDTO.getCustomerId()))
                .orElseThrow(() -> new RuntimeException("Customer not found: " + orderDTO.getCustomerId()));

        Order order = new Order();
        order.setDate(orderDTO.getDate());
        order.setCustomer(customer);

        List<OrderDetail> details = new ArrayList<>();

        for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
            Item item = itemRepo.findById(detailDTO.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found: " + detailDTO.getItemId()));

            if (item.getQtyOnHand() < detailDTO.getQty()) {
                throw new RuntimeException("Insufficient stock for item: " + item.getId());
            }


            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getQty());
            itemRepo.save(item);


            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setItem(item);
            orderDetail.setQty(detailDTO.getQty());
            orderDetail.setUnitPrice(detailDTO.getUnitPrice());

            details.add(orderDetail);
        }

        order.setOrderDetails(details);

        orderRepo.save(order);
    }
}
