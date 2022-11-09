package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.RangeKeyCondition;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import iot.converter.CentralIoTHubConverter;
import iot.entity.UserCentralIoTHubMappingEntity;

import java.util.ArrayList;
import java.util.List;

import static iot.constant.constant.CENTRAL_HUB_KEY_PREFIX;
import static iot.constant.constant.USER_PREFIX;

/**
 * The type User and central io t hub mapping repo.
 */
public class UserAndCentralIoTHubMappingRepo {
    private final Table table;

    /**
     * Instantiates a new User and central io t hub mapping repo.
     *
     * @param table the table
     */
    public UserAndCentralIoTHubMappingRepo(Table table) {
        this.table = table;
    }

    /**
     * Save central io t hub for user user central io t hub mapping entity.
     *
     * @param entity the entity
     * @return the user central io t hub mapping entity
     */
    public UserCentralIoTHubMappingEntity saveCentralIoTHubForUser(UserCentralIoTHubMappingEntity entity) {
        final var item = new Item()
                .withPrimaryKey("pk", entity.getPk(), "sk", entity.getSk())
                .withString("user_id", entity.getUserId())
                .withString("hub_id", entity.getHubId());

        this.table.putItem(item);
        return entity;
    }

    /**
     * Gets central io t hub for user.
     *
     * @param userId the user id
     * @return the central io t hub for user
     */
    public List<UserCentralIoTHubMappingEntity> getCentralIoTHubForUser(String userId) {
        final var centralIoTHubList = new ArrayList<UserCentralIoTHubMappingEntity>();
        final var condition = new RangeKeyCondition("sk").beginsWith(CENTRAL_HUB_KEY_PREFIX);
        QuerySpec  query= new QuerySpec()
                .withHashKey("pk", USER_PREFIX + userId)
                .withRangeKeyCondition(condition);

        for (final var item : this.table.query(query)) {
            centralIoTHubList.add(CentralIoTHubConverter.toUserCentralIoTHubMappingEntity(item));
        }

        return centralIoTHubList;
    }
}
