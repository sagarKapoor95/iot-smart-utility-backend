package iot.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.DeviceType;
import org.apache.http.util.Asserts;

/**
 * The Register device request.
 */
@JsonDeserialize(builder = UpdateDeviceRequest.Builder.class)
public class UpdateDeviceRequest {
    private final DeviceType type;
    private final String name;
    private final boolean status;

    /**
     * Instantiates a new Register device request.
     *
     * @param builder the builder
     */
    public UpdateDeviceRequest(Builder builder) {

        this.type = builder.type;
        this.name = builder.name;
        this.status = builder.status;
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
    public static class Builder {
        private DeviceType type;
        private String name;
        private boolean status;

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
         * Build register device request.
         *
         * @return the register device request
         */
        public UpdateDeviceRequest build() {
            return new UpdateDeviceRequest(this);
        }
    }
}
