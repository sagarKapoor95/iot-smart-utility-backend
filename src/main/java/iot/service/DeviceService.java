package iot.service;

import iot.converter.DeviceInfoConverter;
import iot.entity.DeviceInfoEntity;
import iot.exception.CentralHubNotFoundException;
import iot.repository.DeviceInfoRepository;
import iot.repository.HubAndDeviceMappingRepository;
import iot.request.RegisterDeviceRequest;

/**
 * The type Device service.
 */
public class DeviceService {
    private final DeviceInfoRepository deviceInfoRepository;
    private final HubAndDeviceMappingRepository hubAndDeviceMappingRepository;
    private final CentralIoTHubService centralIoTHubService;

    /**
     * Instantiates a new Device service.
     *
     * @param deviceInfoRepository          the device info repository
     * @param centralIoTHubService          the central io t hub service
     * @param hubAndDeviceMappingRepository the hub and device mapping repository
     */
    public DeviceService(DeviceInfoRepository deviceInfoRepository,
                         CentralIoTHubService centralIoTHubService,
                         HubAndDeviceMappingRepository hubAndDeviceMappingRepository) {
        this.deviceInfoRepository = deviceInfoRepository;
        this.centralIoTHubService = centralIoTHubService;
        this.hubAndDeviceMappingRepository = hubAndDeviceMappingRepository;
    }

    /**
     * Register device device info entity.
     *
     * @param request the request
     * @return the device info entity
     * @throws CentralHubNotFoundException the central hub not found exception
     */
    public DeviceInfoEntity registerDevice(String hubId, RegisterDeviceRequest request)
            throws CentralHubNotFoundException {
        final var centralHub = centralIoTHubService.getCentralIoTHub(hubId);

        final var deviceInfoEntity = DeviceInfoConverter.toDeviceInfoEntity(hubId, request);
        final var hubAndDeviceMappingEntity =
                DeviceInfoConverter.toHubAndDeviceInfoEntity(centralHub.getId(), deviceInfoEntity.getId());

        hubAndDeviceMappingRepository.saveHubAndDeviceMappingEntity(hubAndDeviceMappingEntity);
        return this.deviceInfoRepository.saveDeviceInfo(deviceInfoEntity);
    }

    /**
     * Get device device info entity.
     *
     * @param deviceId the device id
     * @return the device info entity
     */
    public DeviceInfoEntity getDevice(String deviceId){
        return this.deviceInfoRepository.getDeviceInfo(deviceId);
    }
}
