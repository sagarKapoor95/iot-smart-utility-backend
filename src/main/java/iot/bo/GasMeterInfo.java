package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.DeviceType;

/**
 * The type Gas meter info.
 */
@JsonDeserialize(builder = GasMeterInfo.Builder.class)
public class GasMeterInfo extends DeviceInfo {
    private final GasDetectSensor gasDetectSensor;
    private final GasFlowSensor gasFlowSensor;

    /**
     * Instantiates a new Gas meter info.
     *
     * @param builder the builder
     */
    public GasMeterInfo(Builder builder) {
        super(builder.id, builder.type, builder.consumption);
        this.gasDetectSensor = builder.gasDetectSensor;
        this.gasFlowSensor = builder.gasFlowSensor;
    }

    /**
     * Gets gas detect sensor.
     *
     * @return the gas detect sensor
     */
    public GasDetectSensor getGasDetectSensor() {
        return gasDetectSensor;
    }

    /**
     * Gets gas flow sensor.
     *
     * @return the gas flow sensor
     */
    public GasFlowSensor getGasFlowSensor() {
        return gasFlowSensor;
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String id;
        private DeviceType type;
        private Metric consumption;
        private GasDetectSensor gasDetectSensor;
        private GasFlowSensor gasFlowSensor;

        private Builder() {
        }

        /**
         * Sets gas detect sensor.
         *
         * @param gasDetectSensor the gas detect sensor
         * @return the gas detect sensor
         */
        public Builder setGasDetectSensor(GasDetectSensor gasDetectSensor) {
            this.gasDetectSensor = gasDetectSensor;
            return this;
        }

        /**
         * Sets gas flow sensor.
         *
         * @param gasFlowSensor the gas flow sensor
         * @return the gas flow sensor
         */
        public Builder setGasFlowSensor(GasFlowSensor gasFlowSensor) {
            this.gasFlowSensor = gasFlowSensor;
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
         * Sets type.
         *
         * @param type the type
         * @return the type
         */
        public Builder setType(DeviceType type) {
            this.type = type;
            return this;
        }

        /**
         * Sets consumption.
         *
         * @param consumption the consumption
         * @return the consumption
         */
        public Builder setConsumption(Metric consumption) {
            this.consumption = consumption;
            return this;
        }

        /**
         * Build gas meter info.
         *
         * @return the gas meter info
         */
        public GasMeterInfo build() {
            return new GasMeterInfo(this);
        }
    }
}
