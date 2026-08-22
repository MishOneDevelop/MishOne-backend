package com.mishone.mishone.controller;

import com.mishone.mishone.model.Servicio;
import com.mishone.mishone.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<Servicio>> getAllServicios() {
        return ResponseEntity.ok(servicioService.getAllServicios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        return servicioService.getServicioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.createServicio(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Integer id, @RequestBody Servicio servicioDetalles) {
        return ResponseEntity.ok(servicioService.updateServicio(id, servicioDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        servicioService.deleteServicio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Servicio>> getServiciosActivos() {
        return ResponseEntity.ok(servicioService.getServiciosActivos());
    }
}
