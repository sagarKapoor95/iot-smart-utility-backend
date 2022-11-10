package iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.PlanType;

import static iot.constant.constant.DEVICE_PREFIX;
import static iot.constant.constant.RESOURCE_UTILIZATION_PLAN_KEY_PREFIX;

/**
 * The Resource utilization plan entity.
 */
@JsonDeserialize(builder = ResourceUtilizationPlanEntity.Builder.class)
public class ResourceUtilizationPlanEntity {
    private final String deviceId;
    private final String id;
    private final PlanType type;
    private final String name;
    private final Long startTimestamp;
    private final Long endTimestamp;
    private final Long totalUnit;

    /**
     * Instantiates a new Resource utilization plan entity.
     *
     * @param builder the builder
     */
    public ResourceUtilizationPlanEntity(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.id = builder.id;
        this.startTimestamp = builder.startTimestamp;
        this.endTimestamp = builder.endTimestamp;
        this.totalUnit = builder.totalUnit;
        this.deviceId = builder.deviceId;
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
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    @JsonIgnore
    public String getPk() {
        return DEVICE_PREFIX + this.deviceId;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    @JsonIgnore
    public String getSk() {
        return RESOURCE_UTILIZATION_PLAN_KEY_PREFIX + this.id;
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
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private PlanType type;
        private String name;
        private Long startTimestamp;
        private Long endTimestamp;
        private Long totalUnit;
        private String deviceId;
        private String id;

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
         * Build resource utilization plan entity.
         *
         * @return the resource utilization plan entity
         */
        public ResourceUtilizationPlanEntity build() {
            return new ResourceUtilizationPlanEntity(this);
        }
    }
}
