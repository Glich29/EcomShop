package org.sbislava.ecomshop.service;

import org.sbislava.ecomshop.dto.Purchase;
import org.sbislava.ecomshop.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
