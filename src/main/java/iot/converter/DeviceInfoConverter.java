package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.DeviceInfoEntity;
import iot.entity.HubAndDeviceMappingEntity;
import iot.enums.DeviceType;
import iot.request.RegisterDeviceRequest;
import iot.request.UpdateDeviceRequest;
import iot.utility.JavaUtil;

import java.time.Instant;

/**
 * The Device info converter.
 */
public final class DeviceInfoConverter {
    /**
     * To device info entity device info entity.
     *
     * @param item the item
     * @return the device info entity
     */
    public static DeviceInfoEntity toDeviceInfoEntity(Item item) {
        return DeviceInfoEntity.builder()
                .setId((String) item.get("device_id"))
                .setHubId(item.getString("hub_id"))
                .setName((String) item.get("device_name"))
                .setCreatedAt(Long.valueOf(String.valueOf(item.get("created_at"))))
                .setStatus((boolean) item.get("status"))
                .setUpdatedAt(Long.valueOf(String.valueOf(item.get("updated_at"))))
                .setType(DeviceType.valueOf((String) item.get("device_type")))
                .build();
    }

    /**
     * To hub and device info entity hub and device mapping entity.
     *
     * @param hubId    the hub id
     * @param deviceid the deviceid
     * @return the hub and device mapping entity
     */
    public static HubAndDeviceMappingEntity toHubAndDeviceInfoEntity(String hubId, String deviceid) {
        return HubAndDeviceMappingEntity.builder()
                .setDeviceId(hubId)
                .setHubId(deviceid)
                .build();
    }

    /**
     * To hub and device info entity hub and device mapping entity.
     *
     * @param item the item
     * @return the hub and device mapping entity
     */
    public static HubAndDeviceMappingEntity toHubAndDeviceInfoEntity(Item item) {
        return HubAndDeviceMappingEntity.builder()
                .setDeviceId(item.getString("device_id"))
                .setHubId(item.getString("hub_id"))
                .build();
    }

    /**
     * To device info entity device info entity.
     *
     * @param request the request
     * @return the device info entity
     */
    public static DeviceInfoEntity toDeviceInfoEntity(RegisterDeviceRequest request) {
        return DeviceInfoEntity.builder()
                .setId(JavaUtil.getSaltString(8))
                .setName(request.getName())
                .setHubId(request.getHubId())
                .setCreatedAt(Instant.now().getEpochSecond())
                .setStatus(Boolean.FALSE)
                .setUpdatedAt(Instant.now().getEpochSecond())
                .setType(request.getType())
                .build();
    }

    /**
     * To device info entity device info entity.
     *
     * @param request the request
     * @param entity  the entity
     * @return the device info entity
     */
    public static DeviceInfoEntity toDeviceInfoEntity(UpdateDeviceRequest request, DeviceInfoEntity entity) {
        return DeviceInfoEntity.builder()
                .setId(entity.getId())
                .setName(request.getName() == null ? entity.getName() : request.getName())
                .setCreatedAt(entity.getCreatedAt())
                .setStatus(request.isStatus() != entity.isStatus() ? request.isStatus() : entity.isStatus())
                .setUpdatedAt(Instant.now().getEpochSecond())
                .setType(request.getType() == null ? entity.getType() : request.getType())
                .build();
    }
}
