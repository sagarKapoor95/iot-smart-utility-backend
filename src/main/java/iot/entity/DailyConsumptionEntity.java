package iot.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.Instant;

import static iot.constant.constant.DAILY_CONSUMPTION_KEY;
import static iot.constant.constant.TIMESTAMP_KEY_PREFIX;

/**
 * The type Daily consumption entity.
 */
@JsonDeserialize(builder = DailyConsumptionEntity.Builder.class)
public class DailyConsumptionEntity {
    private final Double electricityConsumption;
    private final Double waterConsumption;
    private final Double gasConsumption;
    private final Long sk;

    /**
     * Instantiates a new Daily consumption entity.
     *
     * @param builder the builder
     */
    public DailyConsumptionEntity(Builder builder) {
        this.electricityConsumption = builder.electricityConsumption;
        this.waterConsumption = builder.waterConsumption;
        this.gasConsumption = builder.gasConsumption;
        this.sk = builder.sk;
    }

    public String getPk() {
        return DAILY_CONSUMPTION_KEY;
    }

    public String getSk() {
        return TIMESTAMP_KEY_PREFIX + this.sk.toString();
    }

    /**
     * Gets electricity consumption.
     *
     * @return the electricity consumption
     */
    public Double getElectricityConsumption() {
        return electricityConsumption;
    }

    /**
     * Gets water consumption.
     *
     * @return the water consumption
     */
    public Double getWaterConsumption() {
        return waterConsumption;
    }

    /**
     * Gets gas consumption.
     *
     * @return the gas consumption
     */
    public Double getGasConsumption() {
        return gasConsumption;
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
        private Double electricityConsumption;
        private Double waterConsumption;
        private Double gasConsumption;
        private Long sk;

        private Builder() {
        }

        /**
         * Sets electricity consumption.
         *
         * @param electricityConsumption the electricity consumption
         * @return the electricity consumption
         */
        public Builder setElectricityConsumption(Double electricityConsumption) {
            this.electricityConsumption = electricityConsumption;
            return this;
        }

        public Builder setSk(Long sk) {
            this.sk = sk;
            return this;
        }

        /**
         * Sets water consumption.
         *
         * @param waterConsumption the water consumption
         * @return the water consumption
         */
        public Builder setWaterConsumption(Double waterConsumption) {
            this.waterConsumption = waterConsumption;
            return this;
        }

        /**
         * Sets gas consumption.
         *
         * @param gasConsumption the gas consumption
         * @return the gas consumption
         */
        public Builder setGasConsumption(Double gasConsumption) {
            this.gasConsumption = gasConsumption;
            return this;
        }

        /**
         * Build daily consumption entity.
         *
         * @return the daily consumption entity
         */
        public DailyConsumptionEntity build() {
           return new DailyConsumptionEntity(this);
        }
    }
}
