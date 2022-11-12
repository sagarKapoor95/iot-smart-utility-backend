package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import iot.converter.DeviceInfoConverter;
import iot.entity.DeviceInfoEntity;

import static iot.constant.constant.DEVICE_PREFIX;
import static iot.constant.constant.DEVICE_INFO_KEY_PREFIX;

/**
 * The Device info repository.
 */
public class DeviceInfoRepository {
    private final Table table;

    /**
     * Instantiates a new Device info repository.
     *
     * @param table the table
     */
    public DeviceInfoRepository(Table table) {
        this.table = table;
    }

    /**
     * Save device info device info entity.
     *
     * @param deviceInfoEntity the device info entity
     * @return the device info entity
     */
    public DeviceInfoEntity saveDeviceInfo(DeviceInfoEntity deviceInfoEntity) {
        final var item = new Item()
                .withPrimaryKey("pk", deviceInfoEntity.getPk(), "sk", deviceInfoEntity.getSk())
                .withString("hub_id", deviceInfoEntity.getHubId())
                .withString("device_type", deviceInfoEntity.getType().name())
                .withString("device_id", deviceInfoEntity.getId())
                .withString("device_name", deviceInfoEntity.getName())
                .withBoolean("status", deviceInfoEntity.isStatus())
                .withNumber("created_at", deviceInfoEntity.getCreatedAt())
                .withNumber("updated_at", deviceInfoEntity.getUpdatedAt());

        this.table.putItem(item);
        return deviceInfoEntity;
    }

    /**
     * Gets device info.saveDeviceInfo
     *
     * @param deviceId the device id
     * @return the device info
     */
    public DeviceInfoEntity getDeviceInfo(String deviceId) {
        final var item =
                this.table.getItem(new PrimaryKey("pk", DEVICE_PREFIX + deviceId, "sk", DEVICE_INFO_KEY_PREFIX));

        if (item == null) {
            return null;
        }

        return DeviceInfoConverter.toDeviceInfoEntity(item);
    }
}
