package iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.DeviceType;
import org.apache.http.util.Asserts;

import static iot.constant.constant.DEVICE_PREFIX;
import static iot.constant.constant.DEVICE_INFO_KEY_PREFIX;

/**
 * The Device info entity.
 */
@JsonDeserialize(builder = DeviceInfoEntity.Builder.class)
public class DeviceInfoEntity {
    private final String id;
    private final DeviceType type;
    private final String name;
    private final boolean status;
    private final Long createdAt;
    private final Long updatedAt;
    private final String hubId;

    /**
     * Instantiates a new Device info entity.
     *
     * @param builder the builder
     */
    public DeviceInfoEntity(Builder builder) {
        Asserts.notEmpty(builder.id, "device Id cannot be empty");

        this.id = builder.id;
        this.type = builder.type;
        this.name = builder.name;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.hubId = builder.hubId;
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
     * Gets pk.
     *
     * @return the pk
     */
    @JsonIgnore
    public String getPk() {
        return DEVICE_PREFIX + this.id;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    @JsonIgnore
    public String getSk() {
        return DEVICE_INFO_KEY_PREFIX;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Is status boolean.
     *
     * @return the boolean
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Long getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public Long getUpdatedAt() {
        return updatedAt;
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
        private String pk;
        private String sk;
        private DeviceType type;
        private String name;
        private boolean status;
        private String id;
        private Long createdAt;
        private Long updatedAt;
        private String hubId;

        private Builder() {
        }

        /**
         * Sets pk.
         *
         * @param pk the pk
         * @return the pk
         */
        public Builder setPk(String pk) {
            return this;
        }

        /**
         * Sets sk.
         *
         * @param sk the sk
         * @return the sk
         */
        public Builder setSk(String sk) {
            return this;
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
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
         * Sets type.
         *
         * @param type the type
         * @return the type
         */
        public Builder setType(DeviceType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets created at.
         *
         * @param createdAt the created at
         * @return the created at
         */
        public Builder setCreatedAt(Long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Sets updated at.
         *
         * @param updatedAt the updated at
         * @return the updated at
         */
        public Builder setUpdatedAt(Long updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * Sets name.
         *
         * @param name the name
         * @return the name
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets status.
         *
         * @param status the status
         * @return the status
         */
        public Builder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        /**
         * Build device info entity.
         *
         * @return the device info entity
         */
        public DeviceInfoEntity build() {
            return new DeviceInfoEntity(this);
        }
    }
}
