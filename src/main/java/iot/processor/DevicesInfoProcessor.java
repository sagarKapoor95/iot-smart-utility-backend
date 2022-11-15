package iot.processor;

import iot.bo.ElectricityMeterInfo;
import iot.bo.GasMeterInfo;
import iot.bo.WaterMeterInfo;
import iot.entity.DevicesInfoEntity;
import iot.entity.IndicatorStateEntity;
import iot.enums.DeviceType;
import iot.repository.DevicesInfoRepository;

import java.time.Instant;
import java.util.List;

public class DevicesInfoProcessor {
    private final DevicesInfoRepository repository;
    private static int processTimeWindowInSeconds = 60;
    private static int gasPressure = 10;
    private static int gasTemperature = 50;
    private static int gasVelocity = 20;
    private static int electricityVoltage = 230;

    public DevicesInfoProcessor(DevicesInfoRepository repository) {
        this.repository = repository;
    }

    public void processDevicesInfo() {
        try {
            final var endTime= Instant.now().getEpochSecond();
            final var startTime = Instant.now().getEpochSecond() - processTimeWindowInSeconds;
            final var devicesInfo = repository.getDevicesInfoInRange(startTime, endTime);

            final var indicatorStatus = process(devicesInfo);
            repository.saveIndicatorStatus(indicatorStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IndicatorStateEntity process(List<DevicesInfoEntity> devicesInfos) {
        int gasLeakage = 0;
        int gasPressureBool = 0;
        int gasTemperatureBool = 0;
        int gasVelocityBool = 0;


        int totalGasEvents = 0;

        Double waterConsumption = 0.0;
        Double electricityConsumption = 0.0;
        Double gasConsumption = 0.0;

        for (final var deviceInfo : devicesInfos) {
            if (deviceInfo.getDevicesInfo() == null) {
                continue;
            }
            for (final var device: deviceInfo.getDevicesInfo().getDevices()) {
                if (device == null) {
                    continue;
                }

                if (device.getType().equals(DeviceType.GAS_METER)) {
                    final var gasDevice = (GasMeterInfo) device;
                    totalGasEvents+=1;
                    gasLeakage += (gasDevice.getGasDetectSensor().isLeakage() ? 1 : 0);
                    gasPressureBool += (gasDevice.getGasFlowSensor().getPressure().getValue() > gasPressure ? 1 : 0);
                    gasTemperatureBool += (gasDevice.getGasFlowSensor().getTemperature().getValue() > gasTemperature ? 1 : 0);
                    gasVelocityBool += (gasDevice.getGasFlowSensor().getVelocity().getValue() > gasVelocity ? 1 : 0);
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

        return IndicatorStateEntity.builder()
                .setElectricityConsumption(electricityConsumption)
                .setElectricityVoltage(false)
                .setGasConsumption(gasConsumption)
                .setWaterConsumption(waterConsumption)
                .setGasLeakage(totalGasEvents == 0 ? null : (gasLeakage * 100 / totalGasEvents) > 30)
                .setGasPressure(totalGasEvents == 0 ? null : (gasPressureBool * 100 / totalGasEvents) > 30)
                .setGasTemperature(totalGasEvents == 0 ? null :  (gasTemperatureBool * 100 / totalGasEvents) > 30)
                .setGasVelocity(totalGasEvents == 0 ? null :  (gasVelocityBool * 100 / totalGasEvents) > 30)
                .build();
    }
}
