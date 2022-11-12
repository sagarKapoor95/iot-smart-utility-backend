package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

/**
 * The Gas detect sensor.
 */
@JsonDeserialize(builder = GasDetectSensor.Builder.class)
public class GasDetectSensor {
    private final String id;
    private final boolean leakage;
    private final List<String> gasComposition;

    /**
     * Instantiates a new Gas detect sensor.
     *
     * @param builder the builder
     */
    public GasDetectSensor(Builder builder) {
        this.id = builder.id;
        this.leakage = builder.leakage;
        this.gasComposition = builder.gasComposition;
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
     * Is leakage boolean.
     *
     * @return the boolean
     */
    public boolean isLeakage() {
        return leakage;
    }

    /**
     * Gets gas composition.
     *
     * @return the gas composition
     */
    public List<String> getGasComposition() {
        return gasComposition;
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String id;
        private boolean leakage;
        private List<String> gasComposition;

        private Builder() {
        }

        /**
         * A gas detect sensor builder.
         *
         * @return the builder
         */
        public static Builder aGasDetectSensor() {
            return new Builder();
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
         * Sets leakage.
         *
         * @param leakage the leakage
         * @return the leakage
         */
        public Builder setLeakage(boolean leakage) {
            this.leakage = leakage;
            return this;
        }

        /**
         * Sets gas composition.
         *
         * @param gasComposition the gas composition
         * @return the gas composition
         */
        public Builder setGasComposition(List<String> gasComposition) {
            this.gasComposition = gasComposition;
            return this;
        }

        /**
         * Build gas detect sensor.
         *
         * @return the gas detect sensor
         */
        public GasDetectSensor build() {
            return new GasDetectSensor(this);
        }
    }
}
