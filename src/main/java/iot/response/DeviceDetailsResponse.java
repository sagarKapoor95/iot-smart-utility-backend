package iot.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.entity.DeviceInfoEntity;
import iot.entity.IndicatorStateEntity;
import iot.entity.ResourceUtilizationPlanEntity;

import java.util.List;

/**
 * The Get device details response.
 */
@JsonDeserialize(builder = GetAllDevicesResponse.Builder.class)
public class DeviceDetailsResponse {
    private final DeviceInfoEntity meterInfo;
    private final ResourceUtilizationPlanEntity plan;
    private final IndicatorStateEntity indicatorStateEntity;
    private final List<Double> dailyConsumption;

    /**
     * Instantiates a new Get device details response.
     *
     * @param builder the builder
     */
    public DeviceDetailsResponse(Builder builder) {
        this.meterInfo = builder.meterInfo;
        this.plan = builder.plan;
        this.indicatorStateEntity = builder.indicatorStateEntity;
        this.dailyConsumption = builder.dailyConsumption;
    }

    /**
     * Gets meter info.
     *
     * @return the meter info
     */
    public DeviceInfoEntity getMeterInfo() {
        return meterInfo;
    }

    /**
     * Gets plan.
     *
     * @return the plan
     */
    public ResourceUtilizationPlanEntity getPlan() {
        return plan;
    }

    /**
     * Gets indicator state entity.
     *
     * @return the indicator state entity
     */
    public IndicatorStateEntity getIndicatorStateEntity() {
        return indicatorStateEntity;
    }

    /**
     * Gets usage.
     *
     * @return the usage
     */
    public List<Double> getDailyConsumption() {
        return dailyConsumption;
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
        private DeviceInfoEntity meterInfo;
        private ResourceUtilizationPlanEntity plan;
        private IndicatorStateEntity indicatorStateEntity;
        private List<Double> dailyConsumption;

        private Builder() {
        }

        /**
         * Sets meter info.
         *
         * @param meterInfo the meter info
         * @return the meter info
         */
        public Builder setMeterInfo(DeviceInfoEntity meterInfo) {
            this.meterInfo = meterInfo;
            return this;
        }

        /**
         * Sets plan.
         *
         * @param plan the plan
         * @return the plan
         */
        public Builder setPlan(ResourceUtilizationPlanEntity plan) {
            this.plan = plan;
            return this;
        }

        /**
         * Sets indicator state entity.
         *
         * @param indicatorStateEntity the indicator state entity
         * @return the indicator state entity
         */
        public Builder setIndicatorStateEntity(IndicatorStateEntity indicatorStateEntity) {
            this.indicatorStateEntity = indicatorStateEntity;
            return this;
        }

        /**
         * Sets usage.
         *
         * @param dailyConsumption the usage
         * @return the usage
         */
        public Builder setDailyConsumption(List<Double> dailyConsumption) {
            this.dailyConsumption = dailyConsumption;
            return this;
        }

        /**
         * Build get device details response.
         *
         * @return the get device details response
         */
        public DeviceDetailsResponse build() {
            return new DeviceDetailsResponse(this);
        }
    }
}
