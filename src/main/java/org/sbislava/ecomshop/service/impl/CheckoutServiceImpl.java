package org.sbislava.ecomshop.service.impl;

import jakarta.transaction.Transactional;
import org.sbislava.ecomshop.dto.Purchase;
import org.sbislava.ecomshop.dto.PurchaseResponse;
import org.sbislava.ecomshop.entity.Customer;
import org.sbislava.ecomshop.entity.Order;
import org.sbislava.ecomshop.entity.OrderItem;
import org.sbislava.ecomshop.repository.CustomerRepository;
import org.sbislava.ecomshop.service.CheckoutService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {


    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        //retrieve the order info from dto
        Order order = purchase.getOrder();
        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        //populate order with order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);
        //populate order with billingAddress and shippingAddress
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        //populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);
        //save to the DB
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID number (UUID version-4);

        return UUID.randomUUID().toString();
    }
}
