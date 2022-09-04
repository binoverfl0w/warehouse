package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.OrderBasicDTO;
import org.example.warehouse.application.rest.dto.OrderDTO;
import org.example.warehouse.application.rest.dto.PageDTO;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.order.OrderItem;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Object> getOrderPage(@RequestParam(required = false) Set<String> statuses, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;

        PageVO<Order> orders;

        if (statuses == null) orders = orderService.getOrderPage(page, size);
        else orders = orderService.getOrderPageFilterStatus(statuses, page, size);

        PageDTO<OrderBasicDTO> ordersDTO = new PageDTO<>();
        ordersDTO.setItems(orders.getItems().stream().map(OrderBasicDTO::fromDomainOrder).collect(Collectors.toList()));
        ordersDTO.setTotalItems(orders.getTotalItems());
        ordersDTO.setTotalPages(orders.getTotalPages());
        ordersDTO.setCurrentPage(orders.getCurrentPage());
        return ResponseEntity.ok(ordersDTO);
    }
}

