package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.PageDTO;
import org.example.warehouse.application.rest.dto.TruckDTO;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTruck(@PathVariable Long id) {
        return ResponseEntity.ok(TruckDTO.fromDomainTruck(truckService.getTruck(id)));
    }

    @PostMapping
    public ResponseEntity<Object> createTruck(@RequestBody TruckDTO truckDTO) {
        return ResponseEntity.ok(TruckDTO.fromDomainTruck(truckService.createTruck(truckDTO.toDomainTruck())));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateTruck(@PathVariable Long id, @RequestBody TruckDTO truckDTO) {
        truckDTO.setId(id);
        return ResponseEntity.ok(TruckDTO.fromDomainTruck(truckService.updateTruck(truckDTO.toDomainTruck())));
    }
}
