package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.DailyConsumptionEntity;

public final class DailyConsumptionsConverter {
    public static DailyConsumptionEntity toDailyConsumptionEntity(Item item) {

        return DailyConsumptionEntity.builder()
                .setElectricityConsumption(item.getDouble("electricity_consumption"))
                .setGasConsumption(item.getDouble("gas_consumption"))
                .setWaterConsumption(item.getDouble("water_consumption"))
                .build();
    }
}
