package iot.enums;

/**
 * The Device type.
 */
public enum DeviceType {
    /**
     * Electricity meter device type.
     */
    ELECTRICITY_METER("m3"),
    /**
     * Water meter device type.
     */
    WATER_METER("m3"),
    /**
     * Wifi meter device type.
     */
    WIFI_METER("m3"),
    /**
     * Gas meter device type.
     */
    GAS_METER("m3");

    DeviceType(String unit) {

    }
}
