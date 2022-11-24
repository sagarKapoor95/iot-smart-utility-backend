package iot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.Instant;

import static iot.constant.constant.INDICATOR_STATUS_KEY;
import static iot.constant.constant.TIMESTAMP_KEY_PREFIX;

/**
 * The type Indicator state entity.
 */
@JsonDeserialize(builder = IndicatorStateEntity.Builder.class)
public class IndicatorStateEntity {
    private final boolean systemActive;
    private final boolean gasLeakage;
    private final boolean waterLeakage;
    private final boolean gasPressure;
    private final boolean gasTemperature;
    private final boolean gasVelocity;
    private final boolean electricityVoltage;
    private final Double waterConsumption;
    private final Double electricityConsumption;
    private final Double gasConsumption;
    private final boolean processed;

    /**
     * Instantiates a new Indicator state entity.
     *
     * @param builder the builder
     */
    public IndicatorStateEntity(Builder builder) {
        this.gasLeakage = builder.gasLeakage;
        this.gasPressure = builder.gasPressure;
        this.gasTemperature = builder.gasTemperature;
        this.gasVelocity = builder.gasVelocity;
        this.electricityVoltage = builder.electricityVoltage;
        this.waterConsumption = builder.waterConsumption;
        this.electricityConsumption = builder.electricityConsumption;
        this.gasConsumption = builder.gasConsumption;
        this.processed = builder.processed;
        this.systemActive = builder.systemActive;
        this.waterLeakage = builder.waterLeakage;
    }

    /**
     * Pk string.
     *
     * @return the string
     */
    @JsonIgnore
    public String getPk() {
        return INDICATOR_STATUS_KEY;
    }

    /**
     * Is water leakage boolean.
     *
     * @return the boolean
     */
    public boolean isWaterLeakage() {
        return waterLeakage;
    }

    /**
     * Sk string.
     *
     * @return the string
     */
    @JsonIgnore
    public String getSk() {
        return TIMESTAMP_KEY_PREFIX+ Instant.now().getEpochSecond();
    }

    /**
     * Is system active boolean.
     *
     * @return the boolean
     */
    public boolean isSystemActive() {
        return systemActive;
    }

    /**
     * Is gas leakage boolean.
     *
     * @return the boolean
     */
    public boolean isGasLeakage() {
        return gasLeakage;
    }

    /**
     * Is gas pressure boolean.
     *
     * @return the boolean
     */
    public boolean isGasPressure() {
        return gasPressure;
    }

    /**
     * Is gas temperature boolean.
     *
     * @return the boolean
     */
    public boolean isGasTemperature() {
        return gasTemperature;
    }

    /**
     * Is gas velocity boolean.
     *
     * @return the boolean
     */
    public boolean isGasVelocity() {
        return gasVelocity;
    }

    /**
     * Is electricity voltage boolean.
     *
     * @return the boolean
     */
    public boolean isElectricityVoltage() {
        return electricityVoltage;
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
     * Gets electricity consumption.
     *
     * @return the electricity consumption
     */
    public Double getElectricityConsumption() {
        return electricityConsumption;
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
     * Is processed boolean.
     *
     * @return the boolean
     */
    public boolean isProcessed() {
        return processed;
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
        private Boolean gasLeakage;
        private Boolean gasPressure;
        private Boolean gasTemperature;
        private Boolean waterLeakage;
        private Boolean gasVelocity;
        private Boolean electricityVoltage;
        private Double waterConsumption;
        private Double electricityConsumption;
        private Double gasConsumption;
        private boolean processed;
        private boolean systemActive;

        private Builder() {
        }

        /**
         * Sets gas leakage.
         *
         * @param gasLeakage the gas leakage
         * @return the gas leakage
         */
        public Builder setGasLeakage(Boolean gasLeakage) {
            this.gasLeakage = gasLeakage;
            return this;
        }

        /**
         * Sets water leakage.
         *
         * @param waterLeakage the water leakage
         * @return the water leakage
         */
        public Builder setWaterLeakage(Boolean waterLeakage) {
            this.waterLeakage = waterLeakage;
            return this;
        }

        /**
         * Sets system active.
         *
         * @param systemActive the system active
         * @return the system active
         */
        public Builder setSystemActive(Boolean systemActive) {
            this.systemActive = systemActive;
            return this;
        }

        /**
         * Sets gas pressure.
         *
         * @param gasPressure the gas pressure
         * @return the gas pressure
         */
        public Builder setGasPressure(Boolean gasPressure) {
            this.gasPressure = gasPressure;
            return this;
        }

        /**
         * Sets gas temperature.
         *
         * @param gasTemperature the gas temperature
         * @return the gas temperature
         */
        public Builder setGasTemperature(Boolean gasTemperature) {
            this.gasTemperature = gasTemperature;
            return this;
        }

        /**
         * Sets gas velocity.
         *
         * @param gasVelocity the gas velocity
         * @return the gas velocity
         */
        public Builder setGasVelocity(Boolean gasVelocity) {
            this.gasVelocity = gasVelocity;
            return this;
        }

        /**
         * Sets electricity voltage.
         *
         * @param electricityVoltage the electricity voltage
         * @return the electricity voltage
         */
        public Builder setElectricityVoltage(Boolean electricityVoltage) {
            this.electricityVoltage = electricityVoltage;
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
         * Sets electricity consumption.
         *
         * @param electricityConsumption the electricity consumption
         * @return the electricity consumption
         */
        public Builder setElectricityConsumption(Double electricityConsumption) {
            this.electricityConsumption = electricityConsumption;
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
         * Sets processed.
         *
         * @param processed the processed
         * @return the processed
         */
        public Builder setProcessed(boolean processed) {
            this.processed = processed;
            return this;
        }

        /**
         * Build indicator state entity.
         *
         * @return the indicator state entity
         */
        public IndicatorStateEntity build() {
            return new IndicatorStateEntity(this);
        }
    }
}
