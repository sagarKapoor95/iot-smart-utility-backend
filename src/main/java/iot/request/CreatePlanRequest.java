package iot.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.PlanType;

/**
 * The Create plan request.
 */
@JsonDeserialize(builder = CreatePlanRequest.Builder.class)
public class CreatePlanRequest {
    private final PlanType type;
    private final String name;
    private final Long startTimestamp;
    private final Long endTimestamp;
    private final Long totalUnit;

    /**
     * Instantiates a new Create plan request.
     *
     * @param builder the builder
     */
    public CreatePlanRequest(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.startTimestamp = builder.startTimestamp;
        this.endTimestamp = builder.endTimestamp;
        this.totalUnit = builder.totalUnit;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public PlanType getType() {
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
     * Gets start timestamp.
     *
     * @return the start timestamp
     */
    public Long getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * Gets end timestamp.
     *
     * @return the end timestamp
     */
    public Long getEndTimestamp() {
        return endTimestamp;
    }

    /**
     * Gets total unit.
     *
     * @return the total unit
     */
    public Long getTotalUnit() {
        return totalUnit;
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
        private PlanType type;
        private String name;
        private Long startTimestamp;
        private Long endTimestamp;
        private Long totalUnit;

        private Builder() {
        }

        /**
         * Sets type.
         *
         * @param type the type
         * @return the type
         */
        public Builder setType(PlanType type) {
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
         * Sets start timestamp.
         *
         * @param startTimestamp the start timestamp
         * @return the start timestamp
         */
        public Builder setStartTimestamp(Long startTimestamp) {
            this.startTimestamp = startTimestamp;
            return this;
        }

        /**
         * Sets end timestamp.
         *
         * @param endTimestamp the end timestamp
         * @return the end timestamp
         */
        public Builder setEndTimestamp(Long endTimestamp) {
            this.endTimestamp = endTimestamp;
            return this;
        }

        /**
         * Sets total unit.
         *
         * @param totalUnit the total unit
         * @return the total unit
         */
        public Builder setTotalUnit(Long totalUnit) {
            this.totalUnit = totalUnit;
            return this;
        }

        /**
         * Build create plan request.
         *
         * @return the create plan request
         */
        public CreatePlanRequest build() {
            return new CreatePlanRequest(this);
        }
    }
}
