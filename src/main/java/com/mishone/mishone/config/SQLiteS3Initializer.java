package com.mishone.mishone.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Descarga el archivo SQLite desde S3 antes de que arranque el contexto de Spring,
 * para que el DataSource apunte a un archivo ya existente.
 */
public class SQLiteS3Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger log = LoggerFactory.getLogger(SQLiteS3Initializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!"true".equals(System.getenv("LOAD_DB"))) {
            log.info("Se indico no cargar el archivo SQLite (LOAD_DB != true).");
            return;
        }

        try {
            S3BackupSupport.Env env = S3BackupSupport.loadEnv();
            S3BackupSupport.download(S3BackupSupport.buildClient(env), env);
            log.info("Archivo SQLite descargado exitosamente desde S3: {}", env.localPath());
        } catch (Exception e) {
            throw new RuntimeException("Error al descargar el archivo SQLite desde S3", e);
        }
    }
}
