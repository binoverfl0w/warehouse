package org.example.warehouse.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.ChassisNumber;
import org.example.warehouse.domain.vo.LicensePlate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "LAST_DELIVERYDATE")
    private LocalDateTime lastDeliveryDate;

    public Truck toDomainTruck() {
        return new Truck(
                id,
                new ChassisNumber(chassisNumber),
                new LicensePlate(licensePlate),
                lastDeliveryDate
        );
    }

    public static TruckEntity fromDomainTruck(Truck truck) {
        TruckEntity mapTruck = new TruckEntity();
        mapTruck.setId(truck.getId());
        mapTruck.setChassisNumber(truck.getChassisNumber().getValue());
        mapTruck.setLicensePlate(truck.getLicensePlate().getValue());
        mapTruck.setLastDeliveryDate(truck.getLastDeliveryDate());
        return mapTruck;
    }
}
