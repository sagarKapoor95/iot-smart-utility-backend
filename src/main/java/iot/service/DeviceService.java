package iot.service;

import iot.bo.DevicesInfo;
import iot.converter.DeviceInfoConverter;
import iot.entity.DeviceInfoEntity;
import iot.enums.DeviceType;
import iot.exception.CentralHubNotFoundException;
import iot.exception.DeviceNotFoundException;
import iot.repository.*;
import iot.request.RegisterDeviceRequest;
import iot.request.UpdateDeviceRequest;
import iot.response.DeviceDetailsResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Device service.
 */
public class DeviceService {
    private final DeviceInfoRepository deviceInfoRepository;
    private final HubAndDeviceMappingRepository hubAndDeviceMappingRepository;
    private final CentralIoTHubRepository centralIoTHubRepository;
    private final ResourceUtilizationPlanRepository resourceUtilizationPlanRepository;
    private final DevicesInfoRepository devicesInfoRepository;

    /**
     * Instantiates a new Device service.
     *
     * @param deviceInfoRepository              the device info repository
     * @param centralIoTHubRepository           the central io t hub repository
     * @param hubAndDeviceMappingRepository     the hub and device mapping repository
     * @param resourceUtilizationPlanRepository the resource utilization plan repository
     * @param devicesInfoRepository             the devices info repository
     */
    public DeviceService(DeviceInfoRepository deviceInfoRepository,
                         CentralIoTHubRepository centralIoTHubRepository,
                         HubAndDeviceMappingRepository hubAndDeviceMappingRepository,
                         ResourceUtilizationPlanRepository resourceUtilizationPlanRepository,
                         DevicesInfoRepository devicesInfoRepository) {
        this.deviceInfoRepository = deviceInfoRepository;
        this.centralIoTHubRepository = centralIoTHubRepository;
        this.hubAndDeviceMappingRepository = hubAndDeviceMappingRepository;
        this.resourceUtilizationPlanRepository = resourceUtilizationPlanRepository;
        this.devicesInfoRepository = devicesInfoRepository;
    }

    /**
     * Register device device info entity.
     *
     * @param hubId   the hub id
     * @param request the request
     * @return the device info entity
     * @throws CentralHubNotFoundException the central hub not found exception
     */
    public DeviceInfoEntity registerDevice(String hubId, RegisterDeviceRequest request)
            throws CentralHubNotFoundException {
        final var centralHub = centralIoTHubRepository.getCentralIoTHubDetails(hubId);

        if (centralHub == null) {
            throw new CentralHubNotFoundException("central hub not found");
        }

        final var deviceInfoEntity = DeviceInfoConverter.toDeviceInfoEntity(hubId, request);
        final var hubAndDeviceMappingEntity =
                DeviceInfoConverter.toHubAndDeviceInfoEntity(centralHub.getId(), deviceInfoEntity.getId());

        hubAndDeviceMappingRepository.saveHubAndDeviceMappingEntity(hubAndDeviceMappingEntity);
        return this.deviceInfoRepository.saveDeviceInfo(deviceInfoEntity);
    }

    public DeviceInfoEntity updateDeviceInfo(UpdateDeviceRequest request, String deviceId)
            throws DeviceNotFoundException {

        final var deviceInfoEntity = deviceInfoRepository.getDeviceInfo(deviceId);
        if (deviceInfoEntity == null) {
            throw new DeviceNotFoundException("device not found");
        }

        final var updatedDeviceInfo = DeviceInfoConverter.toDeviceInfoEntity(request, deviceInfoEntity);
        return deviceInfoRepository.saveDeviceInfo(updatedDeviceInfo);
    }

    /**
     * Get device info entity.
     *
     * @param deviceId the device id
     * @return the device info entity
     */
    public DeviceDetailsResponse getDevice(String deviceId){
        final var deviceInfo = deviceInfoRepository.getDeviceInfo(deviceId);
        List<Double> consumptions = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        if (deviceInfo == null) {
            return null;
        }

        final var planDetails =
                resourceUtilizationPlanRepository.getResourceUtilizationPlanEntity(deviceId);

        var indicatorDetails = devicesInfoRepository.getIndicatorsState(1);
        var dailyConsumption = devicesInfoRepository.getConsumptions(7);

        int size = 7;
        for (var consumption: dailyConsumption) {
            size--;
            if (deviceInfo.getType().equals(DeviceType.ELECTRICITY_METER)) {
                consumptions.set(size, consumption.getElectricityConsumption());
            } else if (deviceInfo.getType().equals(DeviceType.WATER_METER)) {
                consumptions.set(size, consumption.getWaterConsumption());
            } else if (deviceInfo.getType().equals(DeviceType.GAS_METER)) {
                consumptions.set(size, consumption.getGasConsumption());
            }
        }
        return DeviceDetailsResponse.builder()
                .setDailyConsumption(consumptions)
                .setPlan(planDetails)
                .setMeterInfo(deviceInfo)
                .setIndicatorStateEntity((indicatorDetails == null || indicatorDetails.size() == 0)
                        ? null : indicatorDetails.get(0))
                .build();
    }
}