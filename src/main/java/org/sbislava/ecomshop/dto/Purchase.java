package org.sbislava.ecomshop.dto;

import lombok.Data;
import org.sbislava.ecomshop.entity.Address;
import org.sbislava.ecomshop.entity.Customer;
import org.sbislava.ecomshop.entity.Order;
import org.sbislava.ecomshop.entity.OrderItem;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
