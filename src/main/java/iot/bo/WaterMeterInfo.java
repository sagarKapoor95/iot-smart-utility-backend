package iot.bo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iot.enums.DeviceType;

@JsonDeserialize(builder = WaterMeterInfo.Builder.class)
public class WaterMeterInfo extends DeviceInfo{

    public WaterMeterInfo(Builder builder) {
        super(builder.id, DeviceType.WATER_METER, builder.consumption);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String id;
        private DeviceType type;
        private Metric consumption;

        private Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setType(DeviceType type) {
            this.type = type;
            return this;
        }

        public Builder setConsumption(Metric consumption) {
            this.consumption = consumption;
            return this;
        }

        public WaterMeterInfo build() {
            return new WaterMeterInfo(this);
        }
    }
}
