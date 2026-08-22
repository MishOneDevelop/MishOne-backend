package com.mishone.mishone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.file.Paths;

/**
 * Logica compartida de respaldo/restauracion del SQLite contra S3.
 * Antes estaba triplicada (con detalles inconsistentes, como distintos
 * credentials provider) entre SQLiteS3Initializer, SQLiteS3Service y S3UploadService.
 */
public final class S3BackupSupport {

    private static final Logger log = LoggerFactory.getLogger(S3BackupSupport.class);

    public record Env(String accessKey, String secretKey, String region, String bucket, String key, String localPath) {}

    private S3BackupSupport() {
    }

    /** Lee y valida las variables de entorno necesarias para hablar con S3. */
    public static Env loadEnv() {
        Env env = new Env(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET_ACCESS_KEY"),
                System.getenv("AWS_REGION"),
                System.getenv("S3_BUCKET_NAME"),
                System.getenv("S3_OBJECT_KEY"),
                System.getenv("SQLITE_LOCAL_PATH")
        );
        if (env.accessKey() == null || env.secretKey() == null || env.region() == null
                || env.bucket() == null || env.key() == null || env.localPath() == null) {
            throw new IllegalStateException("Faltan variables de entorno necesarias para el respaldo de SQLite en S3.");
        }
        return env;
    }

    public static S3Client buildClient(Env env) {
        return S3Client.builder()
                .region(Region.of(env.region()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(env.accessKey(), env.secretKey())))
                .build();
    }

    /** Reemplaza el archivo SQLite local por la version mas reciente en S3. */
    public static void download(S3Client client, Env env) {
        File localFile = new File(env.localPath());
        if (localFile.exists() && !localFile.delete()) {
            log.warn("No se pudo eliminar el archivo SQLite local existente, se intentara sobreescribir: {}", env.localPath());
        }

        File parentDir = localFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        client.getObject(
                GetObjectRequest.builder().bucket(env.bucket()).key(env.key()).build(),
                Paths.get(env.localPath())
        );
    }

    /** Sube el archivo SQLite local a S3, sobreescribiendo el respaldo existente. */
    public static void upload(S3Client client, Env env) {
        client.putObject(
                PutObjectRequest.builder().bucket(env.bucket()).key(env.key()).build(),
                RequestBody.fromFile(new File(env.localPath()))
        );
    }
}
