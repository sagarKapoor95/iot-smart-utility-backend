package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import iot.converter.CentralIoTHubConverter;
import iot.entity.CentralIoTHubEntity;

import static iot.constant.constant.*;

/**
 * The type Central io t hub repository.
 */
public class CentralIoTHubRepository {
    private final Table table;

    /**
     * Instantiates a new Central io t hub repository.
     *
     * @param table the table
     */
    public CentralIoTHubRepository(Table table) {
        this.table = table;
    }

    /**
     * Save central io t hub details central io t hub entity.
     *
     * @param entity the entity
     * @return the central io t hub entity
     */
    public CentralIoTHubEntity saveCentralIoTHubDetails(CentralIoTHubEntity entity) {
        final var item = new Item()
                .withPrimaryKey("pk", entity.getPk(), "sk", entity.getSk())
                .withString("serial_id", entity.getSerialId())
                .withString("id", entity.getId())
                .withString("name", entity.getName());

        this.table.putItem(item);
        return entity;
    }

    /**
     * Gets central io t hub details.
     *
     * @param id the id
     * @return the central io t hub details
     */
    public CentralIoTHubEntity getCentralIoTHubDetails(String id) {
        final var item =
                this.table.getItem(new PrimaryKey("pk", CENTRAL_HUB_PREFIX, "sk", CENTRAL_HUB_KEY_PREFIX + id));

        if (item == null) {
            return null;
        }

        return CentralIoTHubConverter.toCentralIoTHubEntity(item);
    }
}
