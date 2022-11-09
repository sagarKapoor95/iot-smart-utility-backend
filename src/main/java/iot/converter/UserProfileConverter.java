package iot.converter;

import com.amazonaws.services.dynamodbv2.document.Item;
import iot.entity.UserProfileEntity;
import iot.entity.UserTokenEntity;

/**
 * The type User profile converter.
 */
public final class UserProfileConverter {

    /**
     * To user profile entity user profile entity.
     *
     * @param item the item
     * @return the user profile entity
     */
    public static UserProfileEntity toUserProfileEntity(Item item) {
        return UserProfileEntity.builder()
                .setToken(item.getString("token"))
                .setUserName(item.getString("user_name"))
                .setPassword(item.getString("password"))
                .setName(item.getString("name"))
                .build();
    }

    /**
     * To user profile entity user profile entity.
     *
     * @param userName the user name
     * @param password the password
     * @param token    the token
     * @return the user profile entity
     */
    public static UserProfileEntity toUserProfileEntity(String userName, String password, String token, String name) {
        return UserProfileEntity.builder()
                .setToken(token)
                .setUserName(userName)
                .setPassword(password)
                .setName(name)
                .build();
    }

    /**
     * To user token entity user token entity.
     *
     * @param item the item
     * @return the user token entity
     */
    public static UserTokenEntity toUserTokenEntity(Item item) {
        return UserTokenEntity.builder()
                .setToken(item.getString("token"))
                .setUserName(item.getString("user_name"))
                .setExpiry(item.getNumber("expiry").longValue())
                .setPassword(item.getString("password"))
                .build();
    }

    /**
     * To user token entity user token entity.
     *
     * @param userName the user name
     * @param password the password
     * @param token    the token
     * @param expiry   the expiry
     * @return the user token entity
     */
    public static UserTokenEntity toUserTokenEntity(String userName, String password, String token, Long expiry) {
        return UserTokenEntity.builder()
                .setToken(token)
                .setUserName(userName)
                .setPassword(password)
                .setExpiry(expiry)
                .build();
    }
}
