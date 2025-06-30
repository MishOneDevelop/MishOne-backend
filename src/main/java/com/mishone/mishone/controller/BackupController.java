package com.mishone.mishone.controller;

import com.mishone.mishone.config.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backup")
@RequiredArgsConstructor
public class BackupController {

    private final S3UploadService s3UploadService;

    @PostMapping
    public ResponseEntity<String> triggerManualBackup() {
        try {
            s3UploadService.uploadDatabaseToS3();
            return ResponseEntity.ok("✅ Respaldo manual ejecutado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error al ejecutar el respaldo manual: " + e.getMessage());
        }
    }
}
