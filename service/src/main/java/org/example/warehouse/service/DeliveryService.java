package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.order.OrderItem;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.Status;
import org.example.warehouse.service.exception.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class DeliveryService extends DomainService {
    private TruckService truckService;
    private OrderService orderService;
    private ItemService itemService;

    public DeliveryService(TruckService truckService, OrderService orderService, ItemService itemService) {
        this.truckService = truckService;
        this.orderService = orderService;
        this.itemService = itemService;
    }

    public void deliver(Set<Long> truckIds, Set<Long> orderIds, LocalDateTime deliveryDate) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        Set<Truck> trucks = new HashSet<>();
        Set<Order> orders = new HashSet<>();

        truckIds.forEach((truckId) -> {
            Truck truck = truckService.getTruck(truckId);
            // Check if each truck is available at scheduled date
            if (!truck.isAvailableAtDate(deliveryDate))
                throw new IllegalStateException("Truck with id " + truckId + " is not available at this date");
            trucks.add(truck);
        });

        long[] totalItems = {0}; // Total items to be delivered

        orderIds.forEach((orderId) -> {
            Order order  = orderService.getOrder(orderId);
            // Check if each order is APPROVED
            if (!order.getStatus().getValue().equals(Status.VALUES.APPROVED.name()))
                throw new IllegalStateException("Order with id " + orderId + " cannot be set to be delivered. Please check its status.");
            // Add number of items of this order to the total
            order.getOrderItems().forEach(orderItem -> {
                totalItems[0] += orderItem.getRequestedQuantity().getValue();
            });
            orders.add(order);
        });
        // Check if there are enough trucks
        // Each truck contains a maximum of 10 items
        if (((totalItems[0] - 1) / 10) + 1 > truckIds.size())
            throw new IllegalArgumentException("Not enough trucks to complete delivery.");
        // Set trucks to deliver on delivery date
        truckService.setForDelivery(trucks, deliveryDate);
        // Set orders UNDER_DELIVERY and their delivery date
        orderService.setForDelivery(orders, deliveryDate);
        // Recalculate the quantity on warehouse for each item in the order
        itemService.recalculateItemQuantities(orders);
    }
}
