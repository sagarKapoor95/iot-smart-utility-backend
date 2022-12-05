package iot.configuration;

import com.amazonaws.regions.Regions;

/**
 * The Config constant.
 */
public final class ConfigConstant {
    /**
     * The constant DYNAMODB_TABLE_NAME.
     */
    public static final String DYNAMODB_TABLE_NAME = "IOT_INFO_V1";
    /**
     * The constant REGION.
     */
    public static final Regions REGION = Regions.US_EAST_1;
    /**
     * The constant ACCESS_KEY.
     */
    public static final String ACCESS_KEY = "Test";
    /**
     * The constant SECRET_KEY.
     */
    public static final String SECRET_KEY = "Test";
    /**
     * The constant AWS_DYNAMODB_URL.
     */
    public static final String AWS_DYNAMODB_URL= "https://dynamodb.us-east-1.amazonaws.com";
    /**
     * The constant FCM_NOTIFICATION_URL.
     */
    public static final String FCM_NOTIFICATION_URL = "https://fcm.googleapis.com/fcm/send";

    /**
     * The constant FCM_NOTIFICATION_AUTH_TOKEN.
     */
    public static final String FCM_NOTIFICATION_AUTH_TOKEN = "";
}
