package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.DeviceInfoEntity;
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
                .setName((String) item.get("device_name"))
                .setCreatedAt(Long.valueOf(String.valueOf(item.get("created_at"))))
                .setStatus((boolean) item.get("status"))
                .setUpdatedAt(Long.valueOf(String.valueOf(item.get("updated_at"))))
                .setType(DeviceType.valueOf((String) item.get("device_type")))
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
