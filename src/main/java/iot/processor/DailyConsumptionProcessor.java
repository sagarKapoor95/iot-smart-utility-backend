package iot.processor;

import iot.bo.ElectricityMeterInfo;
import iot.bo.GasMeterInfo;
import iot.bo.WaterMeterInfo;
import iot.entity.DailyConsumptionEntity;
import iot.entity.DevicesInfoEntity;
import iot.entity.IndicatorStateEntity;
import iot.enums.DeviceType;
import iot.repository.DevicesInfoRepository;

import java.time.Instant;
import java.time.Period;
import java.util.List;

public class DailyConsumptionProcessor {
    private final DevicesInfoRepository repository;
    private static Period processTimeWindowInSeconds = Period.ofDays(1);

    public DailyConsumptionProcessor(DevicesInfoRepository repository) {
        this.repository = repository;
    }

    public void processData() {
        final var endTime = Instant.now().getEpochSecond();
        final var startTime = Instant.now().getEpochSecond() - 300;

        final var devices = repository.getDevicesInfoInRange(startTime, endTime);
        final var consumptions = process(devices);
        repository.saveConsumptions(consumptions);
    }

    private DailyConsumptionEntity process(List<DevicesInfoEntity> devicesInfos) {
        Double waterConsumption = 0.0;
        Double electricityConsumption = 0.0;
        Double gasConsumption = 0.0;

        for (final var deviceInfo : devicesInfos) {
            if (deviceInfo.getDevicesInfo() == null) {
                continue;
            }

            for (final var device : deviceInfo.getDevicesInfo().getDevices()) {
                if (device == null) {
                    continue;
                }

                if (device.getType().equals(DeviceType.GAS_METER)) {
                    final var gasDevice = (GasMeterInfo) device;
                    gasConsumption += gasDevice.getConsumption().getValue();
                }

                if (device.getType().equals(DeviceType.ELECTRICITY_METER)) {
                    final var electricityMeterInfo = (ElectricityMeterInfo) device;
                    electricityConsumption += electricityMeterInfo.getConsumption().getValue();
                }

                if (device.getType().equals(DeviceType.WATER_METER)) {
                    final var waterMeterInfo = (WaterMeterInfo) device;
                    waterConsumption += waterMeterInfo.getConsumption().getValue();
                }
            }
        }

        return DailyConsumptionEntity.builder()
                .setElectricityConsumption(electricityConsumption)
                .setGasConsumption(gasConsumption)
                .setWaterConsumption(waterConsumption)
                .build();
    }
}
