package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.truck.ITruckStore;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.entity.TruckEntity;
import org.example.warehouse.infrastructure.repository.TruckRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TruckStore implements ITruckStore {
    private TruckRepository truckRepository;

    public TruckStore(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    public PageVO<Truck> getTruckPage(Integer page, Integer size) {
        Page<TruckEntity> truckEntities = truckRepository.findAll(PageRequest.of(page, size));
        return new PageVO<>(
                truckEntities.getContent().stream().map(TruckEntity::toDomainTruck).collect(Collectors.toList()),
                truckEntities.getTotalElements(),
                truckEntities.getTotalPages(),
                truckEntities.getNumber()
        );
    }

    @Override
    public Optional<Truck> findById(Long id) {
        return truckRepository.findById(id).map(TruckEntity::toDomainTruck);
    }

    @Override
    public Truck save(Truck truck) {
        return truckRepository.save(TruckEntity.fromDomainTruck(truck)).toDomainTruck();
    }

    @Override
    public Set<Truck> findExisting(String chassisNumber, String licensePlate) {
        return truckRepository.findByChassisNumberOrLicensePlate(chassisNumber, licensePlate).stream().map(TruckEntity::toDomainTruck).collect(Collectors.toSet());
    }

    @Override
    public void deleteById(Long id) {
        truckRepository.deleteById(id);
    }
}
