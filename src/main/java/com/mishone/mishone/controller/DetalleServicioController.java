package com.mishone.mishone.controller;

import com.mishone.mishone.model.DetalleServicio;
import com.mishone.mishone.service.DetalleServicioService;
import com.mishone.mishone.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-servicios")
public class DetalleServicioController {

    @Autowired
    private DetalleServicioService detalleServicioService;

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<DetalleServicio>> getAllDetalleServicios() {
        return ResponseEntity.ok(detalleServicioService.getAllDetalleServicios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleServicio> getDetalleById(@PathVariable Integer id) {
        return detalleServicioService.getDetalleServicioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleServicio> createDetalle(@RequestBody DetalleServicio detalle) {
        return ResponseEntity.ok(detalleServicioService.createDetalleServicio(detalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleServicio> updateDetalle(@PathVariable Integer id, @RequestBody DetalleServicio detalleActualizado) {
        return ResponseEntity.ok(detalleServicioService.updateDetalleServicio(id, detalleActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable Integer id) {
        detalleServicioService.deleteDetalleServicio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos")
    public ResponseEntity<List<DetalleServicio>> getDetalleActivos() {
        return ResponseEntity.ok(detalleServicioService.getDetalleServiciosActivos());
    }

    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<DetalleServicio>> getDetalleByServicio(@PathVariable Integer idServicio) {
        return servicioService.getServicioById(idServicio)
                .map(servicio -> ResponseEntity.ok(detalleServicioService.getDetalleServiciosByServicio(servicio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/servicio/{idServicio}/activos")
    public ResponseEntity<List<DetalleServicio>> getDetalleActivosByServicio(@PathVariable Integer idServicio) {
        return servicioService.getServicioById(idServicio)
                .map(servicio -> ResponseEntity.ok(detalleServicioService.getDetalleServiciosActivosByServicio(servicio)))
                .orElse(ResponseEntity.notFound().build());
    }
}
