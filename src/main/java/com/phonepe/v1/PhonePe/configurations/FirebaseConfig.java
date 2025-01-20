package com.phonepe.v1.PhonePe.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        if (FirebaseApp.getApps().isEmpty()) {
        try {
                // Replace the path with the location of your Firebase service account key file
                FileInputStream serviceAccount =
                        new FileInputStream("serviceAccountKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);

                System.out.println("FirebaseApp has been initialized successfully.");
            } catch(IOException e){
                throw new RuntimeException("Failed to initialize Firebase", e);
            }
        }
    }
}
