package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.truck.ITruckStore;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.TruckAlreadyExistsException;
import org.example.warehouse.service.exception.TruckNotFoundException;

import java.util.Optional;
import java.util.Set;

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

    public Truck createTruck(Truck truck) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        truck.isValid();
        Optional<Truck> existingTruck = truckStore.findExisting(truck.getChassisNumber().getValue(), truck.getLicensePlate().getValue()).stream().findFirst();
        if (existingTruck.isPresent())
            throw new TruckAlreadyExistsException("Truck with this chassis number or license plate already exists");
        return truckStore.save(truck);
    }

    public Truck updateTruck(Truck truck) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        Truck toUpdate = truckStore.findById(truck.getId()).orElseThrow(() -> new TruckNotFoundException("id", truck.getId().toString()));
        Set<Truck> existingTrucks = truckStore.findExisting(
                truck.getChassisNumber() == null ? null : truck.getChassisNumber().getValue(),
                truck.getLicensePlate() == null ? null : truck.getLicensePlate().getValue()
        );
        if (existingTrucks.size() > 1 || (existingTrucks.size() == 1 && !existingTrucks.stream().findFirst().get().getId().equals(toUpdate.getId())))
            throw new TruckAlreadyExistsException("Truck with this chassis number or license plate already exists");

        if (truck.getChassisNumber() != null) toUpdate.setChassisNumber(truck.getChassisNumber());
        if (truck.getLicensePlate() != null) toUpdate.setLicensePlate(truck.getLicensePlate());
        toUpdate.isValid();
        return truckStore.save(toUpdate);
    }

    public void deleteTruck(Long id) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        truckStore.findById(id).orElseThrow(() -> new TruckNotFoundException("id", id.toString()));
        truckStore.deleteById(id);
    }
}
