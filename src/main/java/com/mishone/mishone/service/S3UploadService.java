package com.mishone.mishone.service;

import com.mishone.mishone.config.S3BackupSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class S3UploadService {

    public void uploadDatabaseToS3() {
        try {
            log.info("Subiendo archivo SQLite a S3...");
            S3BackupSupport.Env env = S3BackupSupport.loadEnv();
            S3BackupSupport.upload(S3BackupSupport.buildClient(env), env);
            log.info("Archivo SQLite subido exitosamente a S3.");
        } catch (Exception e) {
            log.error("Error al subir archivo SQLite a S3", e);
            throw new RuntimeException("Error al subir el archivo SQLite a S3", e);
        }
    }
}
