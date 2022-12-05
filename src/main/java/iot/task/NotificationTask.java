package iot.task;

import com.google.firebase.messaging.Message;
import iot.lib.FCMLib;
/**
 * The type Notification task.
 */
public class NotificationTask {
    private final FCMLib client;

    /**
     * Instantiates a new Notification task.
     *
     * @param client the client
     */
    public NotificationTask(FCMLib client) {
        this.client = client;
    }

    /**
     * Send central hub down event comm string.
     *
     * @param token the token
     * @return the string
     */
    public String sendCentralHubDownEventComm(String token) {
        final var title = "Disconnect";
        final var description = "Cannot connect to central iot hub. Either Internet or electricity is down";

        return sendComms(title, description, token);
    }

    /**
     * Send gas leakage event comms string.
     *
     * @param token the token
     * @return the string
     */
    public String sendGasLeakageEventComms(String token) {
        final var title = "Leakage Detected";
        final var description = "Gas Flow is Off, as leakage is detected";

        return sendComms(title, description, token);
    }

    public String sendWaterLeakageEventComms(String token) {
        final var title = "Leakage Detected";
        final var description = "Water Leakage detected";

        return sendComms(title, description, token);
    }

    public String sendThresholdBreachedEventsComms(String type, String token) {
        final var title = "Resource Utilization Plan";
        final var description = "Reminder! You have used more than 90% of usage for " + type;

        return sendComms(title, description, token);
    }

    /**
     * Send comms string.
     *
     * @param title       the title
     * @param description the description
     * @param token       the token
     * @return the string
     */
    public String sendComms(String title, String description, String token) {
        try {
            Message message = Message.builder()
                    .putData("title", title)
                    .putData("body", description)
                    .setToken(token)
                    .build();

            return client.sendRequest(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
