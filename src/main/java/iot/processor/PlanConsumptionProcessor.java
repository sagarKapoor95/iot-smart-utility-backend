package iot.processor;

import iot.bo.ElectricityMeterInfo;
import iot.bo.GasMeterInfo;
import iot.bo.WaterMeterInfo;
import iot.entity.DevicesInfoEntity;
import iot.entity.ResourceUtilizationPlanEntity;
import iot.enums.DeviceType;
import iot.enums.PlanType;
import iot.repository.DevicesInfoRepository;
import iot.repository.ResourceUtilizationPlanRepository;
import iot.service.CentralIoTHubService;

import javax.management.timer.Timer;
import java.time.Instant;
import java.util.List;

public class PlanConsumptionProcessor {
    private final DevicesInfoRepository devicesInfoRepository;
    private final CentralIoTHubService centralIoTHubService;


    public PlanConsumptionProcessor(CentralIoTHubService centralIoTHubService,
                                    DevicesInfoRepository devicesInfoRepository) {
        this.centralIoTHubService = centralIoTHubService;
        this.devicesInfoRepository = devicesInfoRepository;
    }

    public void processData() {
        final var user = "sagarkpr";
        try {
            final var centralHubs = centralIoTHubService.getAllDevicesByUser(user);
            for(var centralHub: centralHubs) {
                for (var device: centralHub.getDevices()) {
                    final var plan = centralIoTHubService.getResourceUtilizationPlanEntity(device.getId());
                    final var updatedPlan = calculateConsumptionBasedOnPlan(plan, device.getType());
                    centralIoTHubService.updateResourceUtilizationPlanEntity(updatedPlan);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private ResourceUtilizationPlanEntity calculateConsumptionBasedOnPlan(ResourceUtilizationPlanEntity plan,
                                                                          DeviceType type) {
        var startTime = plan.getStartTimestamp();
        var endTime = Instant.now().getEpochSecond();
        if (plan.getType().equals(PlanType.DAILY)) {
            final var tempEndTime = (endTime-startTime) % (Timer.ONE_DAY/1000);
            startTime = (endTime - tempEndTime);
        } else if (plan.getType().equals(PlanType.WEEKLY)) {
            final var tempEndTime = (endTime-startTime) % (Timer.ONE_WEEK/1000);
            startTime = (endTime - tempEndTime);
        } else if (plan.getType().equals(PlanType.MONTHLY)) {
            final var tempEndTime = (endTime-startTime) % ((Timer.ONE_DAY * 30)/1000);
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
            for (final var device: deviceInfo.getDevicesInfo().getDevices()) {
                if (type.equals(DeviceType.GAS_METER) && device.getType().equals(DeviceType.GAS_METER)) {
                    final var gasDevice = (GasMeterInfo) device;
                    consumption += gasDevice.getConsumption().getValue();
                }
                else if (type.equals(DeviceType.ELECTRICITY_METER) && device.getType().equals(DeviceType.ELECTRICITY_METER)) {
                    final var electricityMeterInfo = (ElectricityMeterInfo) device;
                    consumption += electricityMeterInfo.getConsumption().getValue();
                }
                else if (type.equals(DeviceType.WATER_METER) && device.getType().equals(DeviceType.WATER_METER)) {
                    final var waterMeterInfo = (WaterMeterInfo) device;
                    consumption += waterMeterInfo.getConsumption().getValue();
                }
            }
        }
        return consumption;
    }
}
