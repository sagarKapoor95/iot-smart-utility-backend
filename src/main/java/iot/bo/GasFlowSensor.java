package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The type Gas flow sensor.
 */
@JsonDeserialize(builder = GasFlowSensor.Builder.class)
public class GasFlowSensor {
    private final String id;
    private final Metric pressure;
    private final Metric velocity;
    private final Metric temperature;

    /**
     * Instantiates a new Gas flow sensor.
     *
     * @param builder the builder
     */
    public GasFlowSensor(Builder builder) {
        this.id = builder.id;
        this.pressure = builder.pressure;
        this.velocity = builder.velocity;
        this.temperature = builder.temperature;
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
     * Gets pressure.
     *
     * @return the pressure
     */
    public Metric getPressure() {
        return pressure;
    }

    /**
     * Gets velocity.
     *
     * @return the velocity
     */
    public Metric getVelocity() {
        return velocity;
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public Metric getTemperature() {
        return temperature;
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String id;
        private Metric pressure;
        private Metric velocity;
        private Metric temperature;

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
         * Sets pressure.
         *
         * @param pressure the pressure
         * @return the pressure
         */
        public Builder setPressure(Metric pressure) {
            this.pressure = pressure;
            return this;
        }

        /**
         * Sets velocity.
         *
         * @param velocity the velocity
         * @return the velocity
         */
        public Builder setVelocity(Metric velocity) {
            this.velocity = velocity;
            return this;
        }

        /**
         * Sets temperature.
         *
         * @param temperature the temperature
         * @return the temperature
         */
        public Builder setTemperature(Metric temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Build gas flow sensor.
         *
         * @return the gas flow sensor
         */
        public GasFlowSensor build() {
            return new GasFlowSensor(this);
        }
    }
}
