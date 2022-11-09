package iot.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import iot.converter.UserProfileConverter;
import iot.entity.UserProfileEntity;
import iot.entity.UserTokenEntity;

import static iot.constant.constant.*;

/**
 * The type User repository.
 */
public class UserRepository {
    private final Table table;

    /**
     * Instantiates a new User repository.
     *
     * @param table the table
     */
    public UserRepository(Table table) {
        this.table = table;
    }

    /**
     * Sets user details.
     *
     * @param userProfileEntity the user profile entity
     * @return the user details
     */
    public UserProfileEntity setUserDetails(UserProfileEntity userProfileEntity) {

        var item = new Item()
                .withPrimaryKey("pk", userProfileEntity.getPk(), "sk", userProfileEntity.getSk())
                .withString("user_name", userProfileEntity.getUserName())
                .withString("password", userProfileEntity.getPassword())
                .withString("token", userProfileEntity.getToken());

        if (userProfileEntity.getName() != null) {
            item = item.withString("name", userProfileEntity.getName());
        }

        this.table.putItem(item);
        return userProfileEntity;
    }

    /**
     * Gets user details.
     *
     * @param userName the user name
     * @param password the password
     * @return the user details
     */
    public UserProfileEntity getUserDetails(String userName, String password) {
        final var item =
                this.table.getItem(new PrimaryKey("pk", USER_PREFIX + userName, "sk", USER_PROFILE_SK_PREFIX + password));

        if (item == null) {
            return null;
        }

        return UserProfileConverter.toUserProfileEntity(item);
    }

    /**
     * Sets user token.
     *
     * @param userTokenEntity the user token entity
     * @return the user token
     */
    public UserTokenEntity setUserToken(UserTokenEntity userTokenEntity) {
        final var item = new Item()
                .withPrimaryKey("pk", userTokenEntity.getPk(), "sk", userTokenEntity.getSk())
                .withString("user_name", userTokenEntity.getUserName())
                .withString("password", userTokenEntity.getPassword())
                .withString("token", userTokenEntity.getToken())
                .withNumber("expiry", userTokenEntity.getExpiry());

        this.table.putItem(item);
        return userTokenEntity;
    }

    /**
     * Gets user token.
     *
     * @param token the token
     * @return the user token
     */
    public UserTokenEntity getUserToken(String token) {
        final var item =
                this.table.getItem(new PrimaryKey("pk", USER_TOKEN_PK_PREFIX + token, "sk", USER_TOKEN_SK_PREFIX));

        if (item == null) {
            return null;
        }

        return UserProfileConverter.toUserTokenEntity(item);
    }
}
