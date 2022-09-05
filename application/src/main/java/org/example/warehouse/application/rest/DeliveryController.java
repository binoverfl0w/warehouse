package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.DeliveryDTO;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.Status;
import org.example.warehouse.service.DeliveryService;
import org.example.warehouse.service.OrderService;
import org.example.warehouse.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    private DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<Object> scheduleDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        if (deliveryDTO.getTruckIds() == null) throw new IllegalArgumentException("Truck IDs are required");
        if (deliveryDTO.getOrderIds() == null) throw new IllegalArgumentException("Order IDs are required");
        if (deliveryDTO.getDeliveryDate() == null) throw new IllegalArgumentException("Delivery date is required");
        deliveryService.deliver(deliveryDTO.getTruckIds(), deliveryDTO.getOrderIds(), deliveryDTO.getDeliveryDate());
        return ResponseEntity.ok(null);
    }
}
