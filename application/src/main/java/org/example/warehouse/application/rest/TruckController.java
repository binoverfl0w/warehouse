package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.PageDTO;
import org.example.warehouse.application.rest.dto.TruckDTO;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/trucks")
public class TruckController {
    private TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping
    public ResponseEntity<Object> getTruckPage(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        PageVO<Truck> trucks = truckService.getTruckPage(page, size);
        PageDTO<TruckDTO> trucksDTO = new PageDTO<>();
        trucksDTO.setItems(trucks.getItems().stream().map(TruckDTO::fromDomainTruck).collect(Collectors.toList()));
        trucksDTO.setTotalItems(trucks.getTotalItems());
        trucksDTO.setTotalPages(trucks.getTotalPages());
        trucksDTO.setCurrentPage(trucks.getCurrentPage());
        return ResponseEntity.ok(trucksDTO);
    }
}