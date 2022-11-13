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
    private static int processTimeWindowInSeconds = 600;
    private static int gasPressure = 3000;
    private static int gasTemperature = 50; //https://psc.nebraska.gov/sites/psc.nebraska.gov/files/doc/Pipeline%20operation%20and%20impact%20on%20land%20values%20memo.pdf
    private static int gasVelocity = 30; //https://www.phcppros.com/articles/10964-flow-equations-for-high-pressure-natural-gas
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
        boolean gasLeakage = false;
        boolean gasPressureBool = false;
        boolean gasTemperatureBool= false;
        boolean gasVelocityBool= false;
        boolean electricityVoltageBool= false;
        Double waterConsumption = 0.0;
        Double electricityConsumption = 0.0;
        Double gasConsumption = 0.0;

        for (final var deviceInfo : devicesInfos) {
            if (deviceInfo.getDevicesInfo() == null) {
                continue;
            }
            gasPressureBool = true;
            gasTemperatureBool= true;
            gasVelocityBool= true;
            gasLeakage=true;
            for (final var device: deviceInfo.getDevicesInfo().getDevices()) {
                if (device == null) {
                    continue;
                }

                if (device.getType().equals(DeviceType.GAS_METER)) {
                    final var gasDevice = (GasMeterInfo) device;
                    gasLeakage &= gasDevice.getGasDetectSensor().isLeakage();
                    gasPressureBool &= (gasDevice.getGasFlowSensor().getPressure().getValue() > gasPressure);
                    gasTemperatureBool &= (gasDevice.getGasFlowSensor().getTemperature().getValue() > gasTemperature);
                    gasVelocityBool &= (gasDevice.getGasFlowSensor().getVelocity().getValue() > gasVelocity);
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
                .setElectricityVoltage(electricityVoltageBool)
                .setGasConsumption(gasConsumption)
                .setWaterConsumption(waterConsumption)
                .setGasLeakage(gasLeakage)
                .setGasPressure(gasPressureBool)
                .setGasTemperature(gasTemperatureBool)
                .setGasVelocity(gasVelocityBool)
                .build();
    }
}
