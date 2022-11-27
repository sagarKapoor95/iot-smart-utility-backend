package iot.processor;

import iot.bo.ElectricityMeterInfo;
import iot.bo.GasMeterInfo;
import iot.bo.WaterMeterInfo;
import iot.entity.CommsEntity;
import iot.entity.DevicesInfoEntity;
import iot.entity.IndicatorStateEntity;
import iot.entity.ResourceUtilizationPlanEntity;
import iot.enums.DeviceType;
import iot.enums.EventType;
import iot.enums.PlanType;
import iot.lib.FCMLib;
import iot.repository.DevicesInfoRepository;
import iot.repository.ResourceUtilizationPlanRepository;
import iot.request.FCMRequest;
import iot.service.CentralIoTHubService;
import iot.task.NotificationTask;

import javax.management.timer.Timer;
import java.time.Instant;
import java.util.List;

import static iot.constant.constant.COMMS_TOKEN;
import static iot.constant.constant.USER_NAME;

public class PlanConsumptionProcessor {
    private final DevicesInfoRepository devicesInfoRepository;
    private final CentralIoTHubService centralIoTHubService;
    private final NotificationTask notificationTask;


    public PlanConsumptionProcessor(CentralIoTHubService centralIoTHubService,
                                    DevicesInfoRepository devicesInfoRepository) {
        this.centralIoTHubService = centralIoTHubService;
        this.devicesInfoRepository = devicesInfoRepository;
        this.notificationTask = new NotificationTask(new FCMLib());;
    }

    public void processData() {
        final var user = USER_NAME;
        try {
            final var centralHubs = centralIoTHubService.getAllDevicesByUser(user);
            for (var centralHub : centralHubs) {
                for (var device : centralHub.getDevices()) {
                    final var plan =
                            centralIoTHubService.getResourceUtilizationPlanEntity(device.getMeterInfo().getId());
                    final var updatedPlan =
                            calculateConsumptionBasedOnPlan(plan, device.getMeterInfo().getType());
                    final var entity =
                            centralIoTHubService.updateResourceUtilizationPlanEntity(updatedPlan);
                    sendComms(entity);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendComms(ResourceUtilizationPlanEntity planEntity) {
        final var token = COMMS_TOKEN;
        try {
            if ((planEntity.getConsumption() * 100) / planEntity.getTotalUnit() > 90) {
                final var oldEvent = devicesInfoRepository.getCommsEvent(EventType.PLAN_THRESHOLD_BREACH);
                if (oldEvent == null || (oldEvent.getTimestamp() + 3600 < Instant.now().getEpochSecond())) {
                    final var response =
                            notificationTask.sendThresholdBreachedEventsComms(planEntity.getType().name(), token);

                    final var entity = CommsEntity.builder()
                            .setCommsResponse(response)
                            .setUserId(USER_NAME)
                            .setToken(token)
                            .setEventType(EventType.PLAN_THRESHOLD_BREACH)
                            .setTimestamp(Instant.now().getEpochSecond())
                            .build();

                    devicesInfoRepository.saveCommsEvent(entity);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResourceUtilizationPlanEntity calculateConsumptionBasedOnPlan(ResourceUtilizationPlanEntity plan,
                                                                          DeviceType type) {
        var startTime = plan.getStartTimestamp();
        var endTime = Instant.now().getEpochSecond();
        if (plan.getType().equals(PlanType.DAILY)) {
            final var tempEndTime = (endTime - startTime) % (Timer.ONE_DAY / 1000);
            startTime = (endTime - tempEndTime);
        } else if (plan.getType().equals(PlanType.WEEKLY)) {
            final var tempEndTime = (endTime - startTime) % (Timer.ONE_WEEK / 1000);
            startTime = (endTime - tempEndTime);
        } else if (plan.getType().equals(PlanType.MONTHLY)) {
            final var tempEndTime = (endTime - startTime) % ((Timer.ONE_DAY * 30) / 1000);
            startTime = (endTime - tempEndTime);
        } else {
            endTime = plan.getEndTimestamp();
        }

        final var devicesInfo = devicesInfoRepository.getDevicesInfoInRange(startTime, endTime);
        final var consumption = process(devicesInfo, type);

        return ResourceUtilizationPlanEntity.builder()
                .setConsumption(consumption)
                .setType(plan.getType())
                .setTotalUnit(plan.getTotalUnit())
                .setName(plan.getName())
                .setStartTimestamp(plan.getStartTimestamp())
                .setId(plan.getId())
                .setEndTimestamp(plan.getEndTimestamp())
                .setDeviceId(plan.getDeviceId())
                .build();
    }

    private Double process(List<DevicesInfoEntity> devicesInfos, DeviceType type) {
        double consumption = 0.0;

        for (final var deviceInfo : devicesInfos) {
            for (final var device : deviceInfo.getDevicesInfo().getDevices()) {
                if (type.equals(DeviceType.GAS_METER) && device.getType().equals(DeviceType.GAS_METER)) {
                    final var gasDevice = (GasMeterInfo) device;
                    consumption += gasDevice.getConsumption().getValue();
                } else if (type.equals(DeviceType.ELECTRICITY_METER) && device.getType().equals(DeviceType.ELECTRICITY_METER)) {
                    final var electricityMeterInfo = (ElectricityMeterInfo) device;
                    consumption += electricityMeterInfo.getConsumption().getValue();
                } else if (type.equals(DeviceType.WATER_METER) && device.getType().equals(DeviceType.WATER_METER)) {
                    final var waterMeterInfo = (WaterMeterInfo) device;
                    consumption += waterMeterInfo.getConsumption().getValue();
                }
            }
        }
        return consumption;
    }
}
