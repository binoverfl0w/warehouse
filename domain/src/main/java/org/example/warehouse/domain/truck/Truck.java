package org.example.warehouse.domain.truck;

import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.DomainModel;
import org.example.warehouse.domain.vo.ChassisNumber;
import org.example.warehouse.domain.vo.LicensePlate;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter

public class Truck extends DomainModel {
    private ChassisNumber chassisNumber;
    private LicensePlate licensePlate;
    private Set<LocalDateTime> deliveryDates;

    public Truck(Long id, ChassisNumber chassisNumber, LicensePlate licensePlate, Set<LocalDateTime> deliveryDates) {
        super(id);
        this.chassisNumber = chassisNumber;
        this.licensePlate = licensePlate;
        this.deliveryDates = deliveryDates;
    }

    public void isValid() {
        if (chassisNumber == null) throw new IllegalArgumentException("Chassis number is required");
        if (licensePlate == null) throw new IllegalArgumentException("License plate is required");
    }

    public boolean isAvailableAtDate(LocalDateTime schedule) {
        boolean[] available = {true};
        deliveryDates.forEach(deliveryDate -> {
            if (deliveryDate.getDayOfYear() == schedule.getDayOfYear() && deliveryDate.getYear() == deliveryDate.getYear()) available[0] = false;
        });
        return available[0] && !schedule.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
