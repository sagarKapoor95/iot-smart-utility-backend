package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.ResourceUtilizationPlanEntity;
import iot.enums.PlanType;
import iot.request.CreatePlanRequest;
import iot.utility.JavaUtil;

import java.time.Instant;

/**
 * The type Resource plan utilization converter.
 */
public final class ResourcePlanUtilizationConverter {
    /**
     * To resource plan utilization entity resource utilization plan entity.
     *
     * @param deviceId the device id
     * @param request  the request
     * @return the resource utilization plan entity
     */
    public static ResourceUtilizationPlanEntity toResourcePlanUtilizationEntity(String deviceId,
                                                                                CreatePlanRequest request) {
        return ResourceUtilizationPlanEntity.builder()
                .setDeviceId(deviceId)
                .setId(JavaUtil.getSaltString(8))
                .setName(request.getName())
                .setEndTimestamp(request.getEndTimestamp())
                .setStartTimestamp(request.getStartTimestamp() == null
                        ? Instant.now().getEpochSecond() : request.getStartTimestamp())
                .setType(request.getType())
                .setTotalUnit(request.getTotalUnit())
                .build();
    }

    /**
     * To resource plan utilization entity resource utilization plan entity.
     *
     * @param item the item
     * @return the resource utilization plan entity
     */
    public static ResourceUtilizationPlanEntity toResourcePlanUtilizationEntity(Item item) {

        return ResourceUtilizationPlanEntity.builder()
                .setDeviceId(item.getString("device_id"))
                .setId(item.getString("id"))
                .setName(item.getString("name"))
                .setEndTimestamp(item.getLong("end_time"))
                .setStartTimestamp(item.getLong("start_time"))
                .setType(PlanType.valueOf(item.getString("type")))
                .setTotalUnit(item.getLong("total_unit"))
                .build();
    }
}
