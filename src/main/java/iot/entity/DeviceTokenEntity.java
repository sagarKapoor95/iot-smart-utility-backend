package iot.entity;

/**
 * The type Device token entity.
 */
public class DeviceTokenEntity {
    private final String deviceToken;

    /**
     * Instantiates a new Device token entity.
     *
     * @param builder the builder
     */
    public DeviceTokenEntity(Builder builder) {
        this.deviceToken = builder.deviceToken;
    }

    /**
     * Gets device token.
     *
     * @return the device token
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * The type Builder.
     */
    public static final class Builder {
        private String deviceToken;

        private Builder() {
        }

        /**
         * Sets device token.
         *
         * @param deviceToken the device token
         * @return the device token
         */
        public Builder setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
            return this;
        }

        /**
         * Build device token entity.
         *
         * @return the device token entity
         */
        public DeviceTokenEntity build() {
            return new DeviceTokenEntity(this);
        }
    }
}
