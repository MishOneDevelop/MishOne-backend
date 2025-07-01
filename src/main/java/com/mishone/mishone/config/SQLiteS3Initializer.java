package com.mishone.mishone.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.File;
import java.nio.file.Paths;

public class SQLiteS3Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
            String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
            String region = System.getenv("AWS_REGION");
            String bucket = System.getenv("S3_BUCKET_NAME");
            String key = System.getenv("S3_OBJECT_KEY");
            String localPath = System.getenv("SQLITE_LOCAL_PATH");

            String loadDb = System.getenv("LOAD_DB");


            System.out.println("🧪 AWS_ACCESS_KEY_ID = " + accessKey);
            System.out.println("🧪 AWS_SECRET_ACCESS_KEY = " + secretKey);
            System.out.println("🧪 LOAD_DB = " + loadDb);

            if (accessKey == null || secretKey == null || region == null || bucket == null || key == null || localPath == null) {
                throw new RuntimeException("Faltan variables de entorno necesarias para descargar el archivo SQLite.");
            }

            if (!loadDb.equals("true")) {
                System.out.println("ℹ️ Se indico no cargar el archivo SQLite.");
            } else {
                // Crear el cliente de S3
                S3Client s3Client = S3Client.builder()
                        .region(Region.of(region))
                        .credentialsProvider(StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)))
                        .build();

                File localFile = new File(localPath);

                // Si ya existe el archivo, eliminarlo
                if (localFile.exists()) {
                    System.out.println("ℹ️ Archivo local ya existe. Eliminando...");
                    if (localFile.delete()) {
                        System.out.println("✅ Archivo local eliminado correctamente.");
                    } else {
                        System.out.println("⚠️ No se pudo eliminar el archivo local. Se sobreescribirá si es posible.");
                    }
                }

                // Asegurar que el directorio existe
                File parentDir = localFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                // Descargar archivo desde S3
                s3Client.getObject(
                        GetObjectRequest.builder()
                                .bucket(bucket)
                                .key(key)
                                .build(),
                        Paths.get(localPath)
                );

                System.out.println("✅ Archivo SQLite descargado exitosamente desde S3: " + localPath);
            }




        } catch (Exception e) {
            throw new RuntimeException("❌ Error al descargar el archivo SQLite desde S3", e);
        }
    }
}

