package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.warehouse.domain.truck.Truck;
import org.example.warehouse.domain.vo.ChassisNumber;
import org.example.warehouse.domain.vo.LicensePlate;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter

public class TruckDTO {
    @JsonProperty(value = "id", index =  0)
    private Long id;

    @JsonProperty(value = "chassis_number", index = 1)
    private String chassisNumber;

    @JsonProperty(value = "license_plate", index = 2)
    private String licensePlate;

    @JsonProperty(value = "delivery_dates", index = 3)
    private Set<LocalDateTime> deliveryDates;

    public Truck toDomainTruck() {
        return new Truck(
                id,
                chassisNumber == null ? null : new ChassisNumber(chassisNumber),
                licensePlate == null ? null : new LicensePlate(licensePlate),
                deliveryDates
        );
    }

    public static TruckDTO fromDomainTruck(Truck truck) {
        TruckDTO mapTruck = new TruckDTO();
        mapTruck.setId(truck.getId());
        mapTruck.setChassisNumber(truck.getChassisNumber().getValue());
        mapTruck.setLicensePlate(truck.getLicensePlate().getValue());
        mapTruck.setDeliveryDates(truck.getDeliveryDates());
        return mapTruck;
    }
}
