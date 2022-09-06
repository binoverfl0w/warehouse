package org.example.warehouse.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.ChassisNumber;
import org.example.warehouse.domain.vo.LicensePlate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "TRUCK")
public class TruckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CHASSIS_NUMBER", nullable = false, unique = true)
    private String chassisNumber;

    @Column(name = "LICENSE_PLATE", nullable = false, unique = true)
    private String licensePlate;

    @ElementCollection
    @CollectionTable(name = "truck_date")
    @JoinColumn(name = "TRUCK_ID")
    private Set<LocalDateTime> deliveryDates;

    public Truck toDomainTruck() {
        return new Truck(
                id,
                new ChassisNumber(chassisNumber),
                new LicensePlate(licensePlate),
                deliveryDates
        );
    }

    public static TruckEntity fromDomainTruck(Truck truck) {
        TruckEntity mapTruck = new TruckEntity();
        mapTruck.setId(truck.getId());
        mapTruck.setChassisNumber(truck.getChassisNumber().getValue());
        mapTruck.setLicensePlate(truck.getLicensePlate().getValue());
        mapTruck.setDeliveryDates(truck.getDeliveryDates());
        return mapTruck;
    }
}
