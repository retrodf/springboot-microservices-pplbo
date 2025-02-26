package com.retryanzani.microservices.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.retryanzani.microservices.order.client.InventoryClient;
import com.retryanzani.microservices.order.dto.OrderRequest;
import com.retryanzani.microservices.order.model.Order;
import com.retryanzani.microservices.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        // 1. Using Mockito
        // 2. Using Wiremock
        var isProductionInStock =  inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductionInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);        
        } else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is out of stock");
        }
        
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        orderRepository.save(order);
    }

}