package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import iot.converter.CentralIoTHubConverter;
import iot.converter.DeviceInfoConverter;
import iot.converter.ResourcePlanUtilizationConverter;
import iot.entity.HubAndDeviceMappingEntity;
import iot.entity.ResourceUtilizationPlanEntity;

import java.util.ArrayList;

import static iot.constant.constant.*;

/**
 * The type Resource utilization plan repository.
 */
public class ResourceUtilizationPlanRepository {
    private final Table table;

    /**
     * Instantiates a new Resource utilization plan repository.
     *
     * @param table the table
     */
    public ResourceUtilizationPlanRepository(Table table) {
        this.table = table;
    }

    /**
     * Save resource utilization plan entity resource utilization plan entity.
     *
     * @param entity the entity
     * @return the resource utilization plan entity
     */
    public ResourceUtilizationPlanEntity saveResourceUtilizationPlanEntity(ResourceUtilizationPlanEntity entity) {
        final var item = new Item()
                .withPrimaryKey("pk", entity.getPk(), "sk", entity.getSk())
                .withString("device_id", entity.getDeviceId())
                .withString("id", entity.getId())
                .withString("name", entity.getName())
                .withString("type", entity.getType().name())
                .withNumber("start_time", entity.getStartTimestamp())
                .withNumber("end_time", entity.getEndTimestamp())
                .withNumber("total_unit", entity.getTotalUnit());

        this.table.putItem(item);
        return entity;
    }

    /**
     * Gets resource utilization plan entity.
     *
     * @param deviceId the device id
     * @return the resource utilization plan entity
     */
    public ResourceUtilizationPlanEntity getResourceUtilizationPlanEntity(String deviceId) {

        final var condition =
                new RangeKeyCondition("sk").beginsWith(RESOURCE_UTILIZATION_PLAN_KEY_PREFIX);

        QuerySpec  query= new QuerySpec()
                .withHashKey("pk", DEVICE_PREFIX + deviceId)
                .withRangeKeyCondition(condition);

        for (final var item : this.table.query(query)) {
            return ResourcePlanUtilizationConverter.toResourcePlanUtilizationEntity(item);
        }

        return null;
    }
}
