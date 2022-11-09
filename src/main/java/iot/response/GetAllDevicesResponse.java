package iot.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.entity.CentralIoTHubEntity;
import iot.entity.DeviceInfoEntity;

import java.util.List;

/**
 * The type Get all devices response.
 */
@JsonDeserialize(builder = GetAllDevicesResponse.Builder.class)
public class GetAllDevicesResponse {
    private final CentralIoTHubEntity hubDetails;
    private final List<DeviceInfoEntity> devices;

    /**
     * Instantiates a new Get all devices response.
     *
     * @param builder the builder
     */
    public GetAllDevicesResponse(Builder builder) {
        this.hubDetails = builder.hubDetails;
        this.devices = builder.devices;
    }

    /**
     * Gets hub details.
     *
     * @return the hub details
     */
    public CentralIoTHubEntity getHubDetails() {
        return hubDetails;
    }

    /**
     * Gets devices.
     *
     * @return the devices
     */
    public List<DeviceInfoEntity> getDevices() {
        return devices;
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
        private CentralIoTHubEntity hubDetails;
        private List<DeviceInfoEntity> devices;

        private Builder() {
        }

        /**
         * Sets hub details.
         *
         * @param hubDetails the hub details
         * @return the hub details
         */
        public Builder setHubDetails(CentralIoTHubEntity hubDetails) {
            this.hubDetails = hubDetails;
            return this;
        }

        /**
         * Sets devices.
         *
         * @param devices the devices
         * @return the devices
         */
        public Builder setDevices(List<DeviceInfoEntity> devices) {
            this.devices = devices;
            return this;
        }

        /**
         * Build get all devices response.
         *
         * @return the get all devices response
         */
        public GetAllDevicesResponse build() {
            return new GetAllDevicesResponse(this);
        }
    }
}
