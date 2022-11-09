package iot.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The Register central io t hub request.
 */
@JsonDeserialize(builder = RegisterCentralIoTHubRequest.Builder.class)
public class RegisterCentralIoTHubRequest {
    private final String serialId;
    private final String name;

    /**
     * Instantiates a new Register central io t hub request.
     *
     * @param builder the builder
     */
    public RegisterCentralIoTHubRequest(Builder builder) {
        this.serialId = builder.serialId;
        this.name = builder.name;
    }

    /**
     * Gets serial id.
     *
     * @return the serial id
     */
    public String getSerialId() {
        return serialId;
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
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String serialId;
        private String name;

        private Builder() {
        }

        /**
         * Sets serial id.
         *
         * @param serialId the serial id
         * @return the serial id
         */
        public Builder setSerialId(String serialId) {
            this.serialId = serialId;
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
         * Build register central io t hub request.
         *
         * @return the register central io t hub request
         */
        public RegisterCentralIoTHubRequest build() {
            return new RegisterCentralIoTHubRequest(this);
        }
    }
}
