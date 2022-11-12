package iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.bo.DevicesInfo;

import java.time.Instant;

import static iot.constant.constant.DEVICE_INFO_KEY_PREFIX;
import static iot.constant.constant.TIMESTAMP_KEY_PREFIX;

/**
 * The type Devices info entity.
 */
@JsonDeserialize(builder = DevicesInfoEntity.Builder.class)
public class DevicesInfoEntity {
    private final DevicesInfo devicesInfo;

    /**
     * Instantiates a new Devices info entity.
     *
     * @param builder the builder
     */
    public DevicesInfoEntity(Builder builder) {
        this.devicesInfo = builder.devicesInfo;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    @JsonIgnore
    public String getPk() {
        return DEVICE_INFO_KEY_PREFIX;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    @JsonIgnore
    public String getSk() {
        return TIMESTAMP_KEY_PREFIX + Instant.now().getEpochSecond();
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public DevicesInfo getDevicesInfo() {
        return devicesInfo;
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
     * The Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private DevicesInfo devicesInfo;

        private Builder() {
        }

        /**
         * Sets payload.
         *
         * @param devicesInfo the payload
         * @return the payload
         */
        public Builder setDevicesInfo(DevicesInfo devicesInfo) {
            this.devicesInfo = devicesInfo;
            return this;
        }

        /**
         * Build devices info entity.
         *
         * @return the devices info entity
         */
        public DevicesInfoEntity build() {
            return new DevicesInfoEntity(this);
        }
    }
}
