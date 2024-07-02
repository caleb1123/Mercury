package com.g3.Jewelry_Auction_System.configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GgDriveConfig {

    @Value("${google.service-account-key}")
    private Resource serviceAccountKey;

    @Bean
    public Drive googleDrive() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(serviceAccountKey.getInputStream())
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                credential.getJsonFactory(),
                credential)
                .setApplicationName("Your Application Name")
                .build();
    }
}
