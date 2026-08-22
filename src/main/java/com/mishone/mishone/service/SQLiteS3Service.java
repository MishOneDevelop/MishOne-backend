package com.mishone.mishone.service;

import com.mishone.mishone.config.S3BackupSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SQLiteS3Service {

    public void descargarBaseDatosDesdeS3() {
        try {
            S3BackupSupport.Env env = S3BackupSupport.loadEnv();
            S3BackupSupport.download(S3BackupSupport.buildClient(env), env);
            log.info("Base de datos actualizada desde S3");
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar base de datos desde S3", e);
        }
    }
}
