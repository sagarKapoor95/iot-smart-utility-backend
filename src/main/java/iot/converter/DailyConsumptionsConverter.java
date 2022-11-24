package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.DailyConsumptionEntity;

import java.time.Instant;

public final class DailyConsumptionsConverter {
    public static DailyConsumptionEntity toDailyConsumptionEntity(Item item) {

        return DailyConsumptionEntity.builder()
                .setElectricityConsumption(item.getDouble("electricity_consumption"))
                .setGasConsumption(item.getDouble("gas_consumption"))
                .setWaterConsumption(item.getDouble("water_consumption"))
                .setSk(Long.valueOf(item.getString("sk").split("#")[1]))
                .build();
    }
}
