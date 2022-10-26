package iot.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Json util.
 */
public final class JsonUtil {
    /**
     * The constant mapper.
     */
    final static ObjectMapper mapper = init();

    private static ObjectMapper init() {
        final var mapper = new ObjectMapper();
        //mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, false);
        return mapper;
    }

    /**
     * Serialize string.
     *
     * @param value the value
     * @return the string
     */
    public static String serialize(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * De serialize t.
     *
     * @param <T>   the type parameter
     * @param value the value
     * @param type  the type
     * @return the t
     */
    public static <T> T deSerialize(String value, Class<T> type) {
        try {
            return mapper.readValue(value, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
