package iot.task;

import com.amazonaws.util.json.JSONObject;
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
            JSONObject message = new JSONObject();
            message.put("to", token);
            message.put("priority", "high");

            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", description);
            message.put("notification", notification);
            return client.sendRequest(message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
