package org.example.warehouse.domain.truck;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Optional;
import java.util.Set;

public interface ITruckStore {
    PageVO<Truck> getTruckPage(Integer page, Integer size);

    Optional<Truck> findById(Long id);

    Truck save(Truck truck);

    Set<Truck> findExisting(String chassisNumber, String licensePlate);

    void deleteById(Long id);
}
