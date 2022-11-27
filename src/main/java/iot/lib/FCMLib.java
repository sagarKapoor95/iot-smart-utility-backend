package iot.lib;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.File;
import java.io.FileInputStream;

public class FCMLib {

    public FCMLib() {
        init();
    }

    private void init() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File cityFile = new File(classLoader.getResource("service-account.json").getFile());
            FileInputStream refreshToken = new FileInputStream(cityFile);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://ase-project-d7b70.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendRequest(Message message) {
        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
