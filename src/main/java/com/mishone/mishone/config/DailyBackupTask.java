package com.mishone.mishone.config;

import com.mishone.mishone.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyBackupTask {

    private final S3UploadService s3UploadService;

    // A las 12:00 a.m. todos los días
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Bogota")
    public void backupDatabase() {
        log.info("🕛 Iniciando tarea de respaldo automático...");
        s3UploadService.uploadDatabaseToS3();
    }
}

