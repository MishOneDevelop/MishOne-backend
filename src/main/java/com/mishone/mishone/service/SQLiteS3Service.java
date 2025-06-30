package com.mishone.mishone.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.File;
import java.nio.file.Paths;

@Service
public class SQLiteS3Service {

    public void descargarBaseDatosDesdeS3() {
        try {
            String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
            String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
            String region = System.getenv("AWS_REGION");
            String bucket = System.getenv("S3_BUCKET_NAME");
            String key = System.getenv("S3_OBJECT_KEY");
            String localPath = System.getenv("SQLITE_LOCAL_PATH");

            if (accessKey == null || secretKey == null || region == null || bucket == null || key == null || localPath == null) {
                throw new RuntimeException("Faltan variables de entorno necesarias para descargar el archivo SQLite.");
            }

            S3Client s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();

            File localFile = new File(localPath);
            if (localFile.exists()) {
                localFile.delete();
            }

            File parentDir = localFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            s3Client.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build(),
                    Paths.get(localPath)
            );

            System.out.println("✅ Base de datos actualizada desde S3");

        } catch (Exception e) {
            throw new RuntimeException("❌ Error al actualizar base de datos desde S3", e);
        }
    }
}

