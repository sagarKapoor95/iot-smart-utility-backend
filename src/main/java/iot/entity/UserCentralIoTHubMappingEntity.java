package iot.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import static iot.constant.constant.CENTRAL_HUB_KEY_PREFIX;
import static iot.constant.constant.USER_PREFIX;

/**
 * The type User central io t hub mapping entity.
 */
@JsonDeserialize(builder = UserCentralIoTHubMappingEntity.Builder.class)
public class UserCentralIoTHubMappingEntity {
    private final String hubId;
    private final String userId;

    /**
     * Instantiates a new User central io t hub mapping entity.
     *
     * @param builder the builder
     */
    public UserCentralIoTHubMappingEntity(Builder builder) {
        this.hubId = builder.hubId;
        this.userId = builder.userId;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    public String getPk() {
        return USER_PREFIX + this.userId;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    public String getSk() {
        return CENTRAL_HUB_KEY_PREFIX + this.hubId;
    }

    /**
     * Gets hub id.
     *
     * @return the hub id
     */
    public String getHubId() {
        return hubId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String hubId;
        private String userId;

        private Builder() {
        }

        /**
         * Sets hub id.
         *
         * @param hubId the hub id
         * @return the hub id
         */
        public Builder setHubId(String hubId) {
            this.hubId = hubId;
            return this;
        }

        /**
         * Sets user id.
         *
         * @param userId the user id
         * @return the user id
         */
        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Build user central io t hub mapping entity.
         *
         * @return the user central io t hub mapping entity
         */
        public UserCentralIoTHubMappingEntity build() {
            return new UserCentralIoTHubMappingEntity(this);
        }
    }
}
