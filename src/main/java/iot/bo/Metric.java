package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The Metric.
 */
@JsonDeserialize(builder = Metric.Builder.class)
public class Metric {
    private final String unit;
    private final Double value;

    /**
     * Instantiates a new Metric.
     *
     * @param builder the builder
     */
    public Metric(Builder builder) {
        this.unit = builder.unit;
        this.value = builder.value;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * The Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String unit;
        private Double value;

        private Builder() {
        }

        /**
         * Sets unit.
         *
         * @param unit the unit
         * @return the unit
         */
        public Builder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        /**
         * Sets value.
         *
         * @param value the value
         * @return the value
         */
        public Builder setValue(Double value) {
            this.value = value;
            return this;
        }

        /**
         * Build metric.
         *
         * @return the metric
         */
        public Metric build() {
            return new Metric(this);
        }
    }
}
