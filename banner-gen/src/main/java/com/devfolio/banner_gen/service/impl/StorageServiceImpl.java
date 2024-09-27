package com.devfolio.banner_gen.service.impl;
import com.devfolio.banner_gen.service.StorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class StorageServiceImpl implements StorageService {

    private final Storage storage;

    public StorageServiceImpl() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/vassudhasingh12/Desktop/backend-service/banner-gen/bb-hackathon-app-d0569ae3445d.json"))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    @Override
    public String getImageUrl() {
        Blob blob = storage.get("banner-bucket", "vecteezy_abstract-paper-background-design-for-presentation-banner_25755008.jpg");

        if (blob == null) {
            throw new RuntimeException("No such object in GCS");
        }

        // Generate a signed URL that is valid for some time (e.g., 1 hour)
        return blob.signUrl(1, TimeUnit.HOURS).toString();
    }
}
