package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

/**
 * The Devices info.
 */
@JsonDeserialize(builder = DevicesInfo.Builder.class)
public class DevicesInfo {
    private final String type;
    private final String id;
    private final List<DeviceInfo> devices;

    /**
     * Instantiates a new Devices info.
     *
     * @param builder the builder
     */
    public DevicesInfo(Builder builder) {
        this.type = builder.type;
        this.id = builder.id;
        this.devices = builder.devices;
    }

    /**
     * Gets central hub.
     *
     * @return the central hub
     */
    public String getType() {
        return type;
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
     * Gets devices.
     *
     * @return the devices
     */
    public List<DeviceInfo> getDevices() {
        return devices;
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String type;
        private String id;
        private List<DeviceInfo> devices;

        private Builder() {
        }

        /**
         * Sets central hub.
         *
         * @param type the central hub
         * @return the central hub
         */
        public Builder setType(String type) {
            this.type = type;
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
         * Sets devices.
         *
         * @param devices the devices
         * @return the devices
         */
        public Builder setDevices(List<DeviceInfo> devices) {
            this.devices = devices;
            return this;
        }

        /**
         * Build devices info.
         *
         * @return the devices info
         */
        public DevicesInfo build() {
            return new DevicesInfo(this);
        }
    }
}
