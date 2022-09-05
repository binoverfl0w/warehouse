package org.example.warehouse.domain.truck;

import org.example.warehouse.domain.vo.PageVO;

public interface ITruckStore {
    PageVO<Truck> getTruckPage(Integer page, Integer size);
}
