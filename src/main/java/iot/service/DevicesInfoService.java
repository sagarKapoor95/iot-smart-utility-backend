package iot.service;

import iot.bo.DevicesInfo;
import iot.entity.DevicesInfoEntity;
import iot.repository.DevicesInfoRepository;

public class DevicesInfoService {
    private final DevicesInfoRepository repository;
    public DevicesInfoService(DevicesInfoRepository repository) {
        this.repository = repository;
    }

    public DevicesInfoEntity registerDevicesInfo(DevicesInfo info) {
        final var devicesInfo = DevicesInfoEntity.builder().setDevicesInfo(info)
                .build();

        repository.saveDevicesInfo(devicesInfo);
        return devicesInfo;
    }
}
