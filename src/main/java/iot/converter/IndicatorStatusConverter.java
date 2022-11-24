package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.IndicatorStateEntity;

public final class IndicatorStatusConverter {
    public static IndicatorStateEntity toIndicatorStateEntity(Item item) {

        return IndicatorStateEntity.builder()
                .setSystemActive(item.getBoolean("system_active"))
                .setWaterConsumption(item.getDouble("water_consumption"))
                .setGasVelocity(item.getBoolean("gas_velocity"))
                .setGasTemperature(item.getBoolean("gas_temperature"))
                .setGasPressure(item.getBoolean("gas_pressure"))
                .setGasLeakage(item.getBoolean("gas_leakage"))
                .setElectricityConsumption(item.getDouble("electricity_consumption"))
                .setGasConsumption(item.getDouble("gas_consumption"))
                .setElectricityVoltage(item.getBoolean("electricity_voltage"))
                .setProcessed(item.getBoolean("processed"))
                .setWaterLeakage(!item.isNull("water_leakage") && item.getBoolean("water_leakage"))
                .build();
    }
}
