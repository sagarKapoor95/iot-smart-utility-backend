package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.CentralIoTHubEntity;
import iot.entity.UserCentralIoTHubMappingEntity;
import iot.utility.JavaUtil;

/**
 * The type Central io t hub converter.
 */
public final class CentralIoTHubConverter {
    /**
     * To central io t hub entity central io t hub entity.
     *
     * @param name     the name
     * @param serialId the serial id
     * @return the central io t hub entity
     */
    public static CentralIoTHubEntity toCentralIoTHubEntity(String name, String serialId) {
        return CentralIoTHubEntity.builder()
                .setId(JavaUtil.getSaltString(8))
                .setName(name)
                .setSerialId(serialId)
                .build();
    }

    /**
     * To central io t hub entity central io t hub entity.
     *
     * @param item the item
     * @return the central io t hub entity
     */
    public static CentralIoTHubEntity toCentralIoTHubEntity(Item item) {
        return CentralIoTHubEntity.builder()
                .setId(item.getString("id"))
                .setName(item.getString("name"))
                .setSerialId(item.getString("serial_id"))
                .build();
    }

    /**
     * To central io t hub and user mapping entity user central io t hub mapping entity.
     *
     * @param userId       the user id
     * @param centralHubId the central hub id
     * @return the user central io t hub mapping entity
     */
    public static UserCentralIoTHubMappingEntity toCentralIoTHubAndUserMappingEntity(String userId,
                                                                                     String centralHubId) {
        return UserCentralIoTHubMappingEntity.builder()
                .setHubId(centralHubId)
                .setUserId(userId)
                .build();
    }

    /**
     * To user central io t hub mapping entity user central io t hub mapping entity.
     *
     * @param item the item
     * @return the user central io t hub mapping entity
     */
    public static UserCentralIoTHubMappingEntity toUserCentralIoTHubMappingEntity(Item item) {
        return UserCentralIoTHubMappingEntity.builder()
                .setHubId(item.get("hub_id").toString())
                .setUserId(item.get("user_id").toString())
                .build();
    }
}
