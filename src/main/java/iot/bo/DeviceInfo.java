package iot.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import iot.enums.DeviceType;

/**
 * The Device info.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ElectricityMeterInfo.class, name = "ELECTRICITY_METER"),
        @JsonSubTypes.Type(value = GasMeterInfo.class, name = "GAS_METER"),
        @JsonSubTypes.Type(value = WaterMeterInfo.class, name = "WATER_METER")}
)
public abstract class DeviceInfo {
    private final String id;
    private final DeviceType type;
    private final Metric consumption;

    /**
     * Instantiates a new Device info.
     *
     * @param id          the id
     * @param type        the type
     * @param consumption the consumption
     */
    public DeviceInfo(String id, DeviceType type, Metric consumption) {
        this.id = id;
        this.type = type;
        this.consumption = consumption;
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
     * Gets type.
     *
     * @return the type
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Gets consumption.
     *
     * @return the consumption
     */
    public Metric getConsumption() {
        return consumption;
    }
}
