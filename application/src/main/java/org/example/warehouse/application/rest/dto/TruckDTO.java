package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.ChassisNumber;
import org.example.warehouse.domain.vo.LicensePlate;

import java.time.LocalDateTime;

@Getter
@Setter

public class TruckDTO {
    @JsonProperty(value = "id", index =  0)
    private Long id;

    @JsonProperty(value = "chassis_number", index = 1)
    private String chassisNumber;

    @JsonProperty(value = "license_plate", index = 2)
    private String licensePlate;

    @JsonProperty(value = "last_deliverydate", index = 3, access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastDeliveryDate;

    @JsonProperty(value = "available", index = 4, access = JsonProperty.Access.READ_ONLY)
    private boolean available;

    public Truck toDomainTruck() {
        return new Truck(
                id,
                chassisNumber == null ? null : new ChassisNumber(chassisNumber),
                licensePlate == null ? null : new LicensePlate(licensePlate),
                lastDeliveryDate
        );
    }

    public static TruckDTO fromDomainTruck(Truck truck) {
        TruckDTO mapTruck = new TruckDTO();
        mapTruck.setId(truck.getId());
        mapTruck.setChassisNumber(truck.getChassisNumber().getValue());
        mapTruck.setLicensePlate(truck.getLicensePlate().getValue());
        mapTruck.setLastDeliveryDate(truck.getLastDeliveryDate());
        mapTruck.setAvailable(truck.isAvailable());
        return mapTruck;
    }
}
