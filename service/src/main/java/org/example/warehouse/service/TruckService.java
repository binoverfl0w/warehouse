package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.truck.ITruckStore;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.TruckNotFoundException;

public class TruckService extends DomainService {
    private ITruckStore truckStore;

    public TruckService(ITruckStore truckStore) {
        this.truckStore = truckStore;
    }

    public PageVO<Truck> getTruckPage(Integer page, Integer size) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        return truckStore.getTruckPage(page, size);
    }

    public Truck getTruck(Long id) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        return truckStore.findById(id).orElseThrow(() -> new TruckNotFoundException("id", id.toString()));
    }
}
