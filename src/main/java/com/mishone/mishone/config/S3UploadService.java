package com.mishone.mishone.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.file.Paths;

@Slf4j
@Service
public class S3UploadService {

    public void uploadDatabaseToS3() {
        try {
            String bucketName = System.getenv("S3_BUCKET_NAME");
            String objectKey = System.getenv("S3_OBJECT_KEY");
            String localPath = System.getenv("SQLITE_LOCAL_PATH");

            log.info("⏫ Subiendo archivo SQLite a S3...");

            S3Client s3Client = S3Client.builder()
                    .region(Region.of(System.getenv("AWS_REGION")))
                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .build();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            File file = new File(localPath);
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

            log.info("✅ Archivo SQLite subido exitosamente a S3.");
        } catch (Exception e) {
            log.error("❌ Error al subir archivo SQLite a S3", e);
            throw new RuntimeException("Error al subir el archivo SQLite a S3", e);
        }
    }
}

