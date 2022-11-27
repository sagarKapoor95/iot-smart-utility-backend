package iot.processor;

import iot.bo.ElectricityMeterInfo;
import iot.bo.GasMeterInfo;
import iot.bo.WaterMeterInfo;
import iot.entity.CommsEntity;
import iot.entity.DevicesInfoEntity;
import iot.entity.IndicatorStateEntity;
import iot.enums.DeviceType;
import iot.enums.EventType;
import iot.lib.FCMLib;
import iot.repository.DevicesInfoRepository;
import iot.task.NotificationTask;

import java.time.Instant;
import java.util.List;

import static iot.constant.constant.COMMS_TOKEN;
import static iot.constant.constant.USER_NAME;

public class DevicesInfoProcessor {
    private final DevicesInfoRepository repository;
    private final NotificationTask notificationTask;
    private static int processTimeWindowInSeconds = 60;
    private static int gasPressure = 2;
    private static int gasTemperature = 50;
    private static int gasVelocity = 20;
    private static int electricityVoltage = 230;

    public DevicesInfoProcessor(DevicesInfoRepository repository) {
        this.repository = repository;
        this.notificationTask = new NotificationTask(new FCMLib());
    }

    public void processDevicesInfo() {
        try {
            final var endTime = Instant.now().getEpochSecond();
            final var startTime = Instant.now().getEpochSecond() - processTimeWindowInSeconds;
            final var devicesInfo = repository.getDevicesInfoInRange(startTime, endTime);

            final var indicatorStatus = process(devicesInfo);
            sendComms(indicatorStatus);
            repository.saveIndicatorStatus(indicatorStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendComms(IndicatorStateEntity indicatorState) {
        final var token = COMMS_TOKEN;
        try {
            if (!indicatorState.isSystemActive()) {
                final var oldEvent = repository.getCommsEvent(EventType.CENTRAL_HUB_DOWN);
                if (oldEvent == null || (oldEvent.getTimestamp() + 3600 < Instant.now().getEpochSecond())) {
                    final var response= notificationTask.sendCentralHubDownEventComm(token);

                    final var entity = CommsEntity.builder()
                            .setCommsResponse(response)
                            .setUserId(USER_NAME)
                            .setToken(token)
                            .setEventType(EventType.CENTRAL_HUB_DOWN)
                            .setTimestamp(Instant.now().getEpochSecond())
                            .build();
                    repository.saveCommsEvent(entity);
                }
            }

            if (indicatorState.isGasLeakage()) {
                final var oldEvent = repository.getCommsEvent(EventType.GAS_LEAKAGE);
                if (oldEvent == null || (oldEvent.getTimestamp() + 3600 < Instant.now().getEpochSecond())) {
                    final var response = notificationTask.sendGasLeakageEventComms(token);

                    final var entity = CommsEntity.builder()
                            .setCommsResponse(response)
                            .setUserId(USER_NAME)
                            .setToken(token)
                            .setEventType(EventType.GAS_LEAKAGE)
                            .setTimestamp(Instant.now().getEpochSecond())
                            .build();
                    repository.saveCommsEvent(entity);
                }
            }

            if (indicatorState.isWaterLeakage()) {
                final var oldEvent = repository.getCommsEvent(EventType.WATER_LEAKAGE);
                if (oldEvent == null || (oldEvent.getTimestamp() + 3600 < Instant.now().getEpochSecond())) {
                    final var response = notificationTask.sendWaterLeakageEventComms(token);

                    final var entity = CommsEntity.builder()
                            .setCommsResponse(response)
                            .setUserId(USER_NAME)
                            .setToken(token)
                            .setEventType(EventType.WATER_LEAKAGE)
                            .setTimestamp(Instant.now().getEpochSecond())
                            .build();
                    repository.saveCommsEvent(entity);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IndicatorStateEntity process(List<DevicesInfoEntity> devicesInfos) {
        int gasLeakage = 0;
        int gasPressureBool = 0;
        int gasTemperatureBool = 0;
        int gasVelocityBool = 0;
        int waterLeakage = 0;


        int totalWaterEvents = 0;
        int totalGasEvents = 0;
        int totalEvents = 0;

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

                totalEvents += 1;

                if (device.getType().equals(DeviceType.GAS_METER)) {
                    final var gasDevice = (GasMeterInfo) device;
                    totalGasEvents += 1;
                    gasLeakage += (gasDevice.getGasDetectSensor().isLeakage() ? 1 : 0);
                    gasPressureBool += (gasDevice.getGasFlowSensor().getPressure().getValue() >= gasPressure ? 1 : 0);
                    gasTemperatureBool += (gasDevice.getGasFlowSensor().getTemperature().getValue() >= gasTemperature ? 1 : 0);
                    gasVelocityBool += (gasDevice.getGasFlowSensor().getVelocity().getValue() > gasVelocity ? 1 : 0);
                    gasConsumption += gasDevice.getConsumption().getValue();
                }

                if (device.getType().equals(DeviceType.ELECTRICITY_METER)) {
                    final var electricityMeterInfo = (ElectricityMeterInfo) device;
                    electricityConsumption += electricityMeterInfo.getConsumption().getValue();
                }

                if (device.getType().equals(DeviceType.WATER_METER)) {
                    final var waterMeterInfo = (WaterMeterInfo) device;
                    totalWaterEvents += 1;
                    waterConsumption += waterMeterInfo.getConsumption().getValue();
                    waterLeakage += (waterMeterInfo.isLeakage() ? 1 : 0);
                }
            }
        }

        return IndicatorStateEntity.builder()
                .setSystemActive(totalEvents > 0)
                .setElectricityConsumption(electricityConsumption)
                .setElectricityVoltage(false)
                .setGasConsumption(gasConsumption)
                .setWaterConsumption(waterConsumption)
                .setWaterLeakage(totalWaterEvents == 0 ? null : (waterLeakage * 100 / totalWaterEvents) > 30)
                .setGasLeakage(totalGasEvents == 0 ? null : (gasLeakage * 100 / totalGasEvents) > 30)
                .setGasPressure(totalGasEvents == 0 ? null : (gasPressureBool * 100 / totalGasEvents) > 30)
                .setGasTemperature(totalGasEvents == 0 ? null : (gasTemperatureBool * 100 / totalGasEvents) > 30)
                .setGasVelocity(totalGasEvents == 0 ? null : (gasVelocityBool * 100 / totalGasEvents) > 30)
                .build();
    }
}
