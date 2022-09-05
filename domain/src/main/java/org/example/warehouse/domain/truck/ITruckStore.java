package org.example.warehouse.domain.truck;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Optional;

public interface ITruckStore {
    PageVO<Truck> getTruckPage(Integer page, Integer size);

    Optional<Truck> findById(Long id);
}
