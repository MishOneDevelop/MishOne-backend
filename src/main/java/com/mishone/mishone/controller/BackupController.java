package com.mishone.mishone.controller;

import com.mishone.mishone.service.S3UploadService;
import com.mishone.mishone.service.SQLiteS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/db")
@RequiredArgsConstructor
public class BackupController {

    private final S3UploadService s3UploadService;
    private final SQLiteS3Service s3Service;

    /**
     * Ejecuta un respaldo manual subiendo el archivo SQLite a S3.
     */
    @PostMapping("/backup")
    public ResponseEntity<String> triggerManualBackup() {
        try {
            s3UploadService.uploadDatabaseToS3();
            return ResponseEntity.ok("✅ Respaldo manual ejecutado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error al ejecutar el respaldo manual: " + e.getMessage());
        }
    }

    /**
     * Reemplaza el archivo SQLite local descargando el más reciente desde S3.
     */
    @PostMapping("/actualizardb")
    public ResponseEntity<String> actualizarBaseDatos() {
        try {
            s3Service.descargarBaseDatosDesdeS3();
            return ResponseEntity.ok("✅ Base de datos actualizada correctamente desde S3.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Error al actualizar la base de datos: " + e.getMessage());
        }
    }
}
