package org.example.warehouse.domain.truck;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.ChassisNumber;
import org.example.warehouse.domain.vo.LicensePlate;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter

public class Truck extends DomainModel {
    private ChassisNumber chassisNumber;
    private LicensePlate licensePlate;
    private LocalDateTime lastDeliveryDate;

    public Truck(Long id, ChassisNumber chassisNumber, LicensePlate licensePlate, LocalDateTime lastDeliveryDate) {
        super(id);
        this.chassisNumber = chassisNumber;
        this.licensePlate = licensePlate;
        this.lastDeliveryDate = lastDeliveryDate;
    }

    public void isValid() {
        if (chassisNumber == null) throw new IllegalArgumentException("Chassis number is required");
        if (licensePlate == null) throw new IllegalArgumentException("License plate is required");
    }

    public boolean isAvailable() {
        return lastDeliveryDate == null || LocalDateTime.now().getDayOfYear() != lastDeliveryDate.getDayOfYear();
    }
}
