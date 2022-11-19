package iot.lib;

import iot.request.FCMRequest;
import iot.utility.JsonUtil;
import okhttp3.*;

import java.io.IOException;

import static iot.configuration.ConfigConstant.FCM_NOTIFICATION_AUTH_TOKEN;
import static iot.configuration.ConfigConstant.FCM_NOTIFICATION_URL;

public class FCMLib {
    private final OkHttpClient client;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public FCMLib() {
        this.client = new OkHttpClient();
    }

    public String sendRequest(String body) {

        Request request = new Request.Builder()
                .url(FCM_NOTIFICATION_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", FCM_NOTIFICATION_AUTH_TOKEN)
                .post(RequestBody.create(body, JSON))
                .build();

        try {
            final var response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
