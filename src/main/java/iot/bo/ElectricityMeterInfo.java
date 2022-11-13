package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.DeviceType;

/**
 * The type Electricity meter info.
 */
@JsonDeserialize(builder = ElectricityMeterInfo.Builder.class)
public class ElectricityMeterInfo extends DeviceInfo{

    /**
     * Instantiates a new Electricity meter info.
     *
     * @param builder the builder
     */
    public ElectricityMeterInfo(Builder builder) {
        super(builder.id, DeviceType.ELECTRICITY_METER, builder.consumption);
    }


    /**
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String id;
        private String type;
        private Metric consumption;

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
         * Sets type.
         *
         * @param type the type
         * @return the type
         */
        public Builder setType(String type) {
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
         * Build electricity meter info.
         *
         * @return the electricity meter info
         */
        public ElectricityMeterInfo build() {
            return new ElectricityMeterInfo(this);
        }
    }
}
