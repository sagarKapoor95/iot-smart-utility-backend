package iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import static iot.constant.constant.CENTRAL_HUB_PREFIX;
import static iot.constant.constant.CENTRAL_HUB_KEY_PREFIX;

/**
 * The Central io t hub entity.
 */
@JsonDeserialize(builder = CentralIoTHubEntity.Builder.class)
public class CentralIoTHubEntity {
    private final String id;
    private final String serialId;
    private final String name;

    /**
     * Instantiates a new Central io t hub entity.
     *
     * @param builder the builder
     */
    public CentralIoTHubEntity(Builder builder) {
        this.id = builder.id;
        this.serialId = builder.serialId;
        this.name = builder.name;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    @JsonIgnore
    public String getPk() {
        return CENTRAL_HUB_PREFIX;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    @JsonIgnore
    public String getSk() {
        return CENTRAL_HUB_KEY_PREFIX + this.id;
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
        private String id;
        private String serialId;
        private String name;

        private Builder() {
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
         * Build central io t hub entity.
         *
         * @return the central io t hub entity
         */
        public CentralIoTHubEntity build() {
            return new CentralIoTHubEntity(this);
        }
    }
}
