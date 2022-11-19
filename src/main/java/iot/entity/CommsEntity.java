package iot.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.EventType;

import static iot.constant.constant.EVENT_KEY;
import static iot.constant.constant.EVENT_KEY_PREFIX;

/**
 * The Comms entity.
 */
@JsonDeserialize(builder = CommsEntity.Builder.class)
public class CommsEntity {
    private final EventType type;
    private final Long timestamp;
    private final String commsResponse;
    private final String userId;
    private final String token;

    /**
     * Instantiates a new Comms entity.
     *
     * @param builder the builder
     */
    public CommsEntity(Builder builder) {
        this.timestamp = builder.timestamp;
        this.commsResponse = builder.commsResponse;
        this.userId = builder.userId;
        this.token = builder.token;
        this.type = builder.type;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    public String getPk() {
        return EVENT_KEY;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    public String getSk() {
        return EVENT_KEY_PREFIX + type.name();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public EventType getType() {
        return type;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Gets comms response.
     *
     * @return the comms response
     */
    public String getCommsResponse() {
        return commsResponse;
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
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private Long timestamp;
        private String commsResponse;
        private String userId;
        private String token;
        private EventType type;


        private Builder() {
        }

        /**
         * Sets timestamp.
         *
         * @param timestamp the timestamp
         * @return the timestamp
         */
        public Builder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Sets event type.
         *
         * @param type the type
         * @return the event type
         */
        public Builder setEventType(EventType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets comms response.
         *
         * @param commsResponse the comms response
         * @return the comms response
         */
        public Builder setCommsResponse(String commsResponse) {
            this.commsResponse = commsResponse;
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
         * Sets token.
         *
         * @param token the token
         * @return the token
         */
        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        /**
         * Build comms entity.
         *
         * @return the comms entity
         */
        public CommsEntity build() {
            return new CommsEntity(this);
        }
    }
}
