package iot.entity;

import static iot.constant.constant.CENTRAL_HUB_KEY_PREFIX;
import static iot.constant.constant.DEVICE_PREFIX;

/**
 * The type Hub and device mapping entity.
 */
public class HubAndDeviceMappingEntity {
    private final String hubId;
    private final String deviceId;

    /**
     * Instantiates a new Hub and device mapping entity.
     *
     * @param builder the builder
     */
    public HubAndDeviceMappingEntity(Builder builder) {
        this.hubId = builder.hubId;
        this.deviceId = builder.deviceId;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    public String getPk() {
        return CENTRAL_HUB_KEY_PREFIX + this.hubId;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    public String getSk() {
        return DEVICE_PREFIX + this.deviceId;
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
     * Gets device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return deviceId;
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
    public static final class Builder {
        private String hubId;
        private String deviceId;

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
         * Sets device id.
         *
         * @param deviceId the device id
         * @return the device id
         */
        public Builder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        /**
         * Build hub and device mapping entity.
         *
         * @return the hub and device mapping entity
         */
        public HubAndDeviceMappingEntity build() {
            return new HubAndDeviceMappingEntity(this);
        }
    }
}
