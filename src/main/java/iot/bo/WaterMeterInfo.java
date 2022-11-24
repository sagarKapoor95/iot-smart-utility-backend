package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.DeviceType;

/**
 * The type Water meter info.
 */
@JsonDeserialize(builder = WaterMeterInfo.Builder.class)
public class WaterMeterInfo extends DeviceInfo{
    private final boolean leakage;

    /**
     * Instantiates a new Water meter info.
     *
     * @param builder the builder
     */
    public WaterMeterInfo(Builder builder) {
        super(builder.id, DeviceType.WATER_METER, builder.consumption);
        this.leakage = builder.leakage;
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
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String id;
        private DeviceType type;
        private Metric consumption;
        private boolean leakage;

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
         * Sets leakage.
         *
         * @param leakage the leakage
         * @return the leakage
         */
        public Builder setLeakage(Boolean leakage) {
            this.leakage = leakage;
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
         * Build water meter info.
         *
         * @return the water meter info
         */
        public WaterMeterInfo build() {
            return new WaterMeterInfo(this);
        }
    }
}
