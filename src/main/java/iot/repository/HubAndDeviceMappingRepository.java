package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import iot.converter.DeviceInfoConverter;
import iot.entity.HubAndDeviceMappingEntity;

import java.util.ArrayList;
import java.util.List;

import static iot.constant.constant.CENTRAL_HUB_KEY_PREFIX;
import static iot.constant.constant.DEVICE_PREFIX;

/**
 * The type Hub and device mapping repository.
 */
public class HubAndDeviceMappingRepository {
    private final Table table;

    /**
     * Instantiates a new Hub and device mapping repository.
     *
     * @param table the table
     */
    public HubAndDeviceMappingRepository(Table table) {
        this.table = table;
    }

    /**
     * Save hub and device mapping entity hub and device mapping entity.
     *
     * @param entity the entity
     * @return the hub and device mapping entity
     */
    public HubAndDeviceMappingEntity saveHubAndDeviceMappingEntity(HubAndDeviceMappingEntity entity) {
        final var item = new Item()
                .withPrimaryKey("pk", entity.getPk(), "sk", entity.getSk())
                .withString("device_id", entity.getDeviceId())
                .withString("hub_id", entity.getHubId());

        this.table.putItem(item);
        return entity;
    }

    /**
     * Gets all devices for hub.
     *
     * @param hubId the hub id
     * @return the all devices for hub
     */
    public List<HubAndDeviceMappingEntity> getAllDevicesForHub(String hubId) {
        final var devices = new ArrayList<HubAndDeviceMappingEntity>();
        final var condition = new RangeKeyCondition("sk").beginsWith(DEVICE_PREFIX);

        QuerySpec query = new QuerySpec()
                .withHashKey("pk", CENTRAL_HUB_KEY_PREFIX + hubId)
                .withRangeKeyCondition(condition);

        for (final var item : this.table.query(query)) {
            devices.add(DeviceInfoConverter.toHubAndDeviceInfoEntity(item));
        }

        return devices;
    }
}
