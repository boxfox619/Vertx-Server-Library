package com.boxfox.vertx.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseUtil {
    public static void init(String firebaseKeystore) throws IOException {
        System.out.println("Initilize firebase");
        FileInputStream serviceAccount = new FileInputStream(firebaseKeystore);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
