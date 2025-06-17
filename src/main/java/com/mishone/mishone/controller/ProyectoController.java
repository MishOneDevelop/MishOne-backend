package com.mishone.mishone.controller;

import com.mishone.mishone.model.Proyecto;
import com.mishone.mishone.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> getAllProyectos() {
        return ResponseEntity.ok(proyectoService.getAllProyectos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Integer id) {
        return proyectoService.getProyectoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proyecto> createProyecto(@RequestBody Proyecto proyecto) {
        return ResponseEntity.ok(proyectoService.createProyecto(proyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable Integer id, @RequestBody Proyecto proyectoDetalles) {
        return ResponseEntity.ok(proyectoService.updateProyecto(id, proyectoDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Integer id) {
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/visibles")
    public ResponseEntity<List<Proyecto>> getProyectosVisibles() {
        return ResponseEntity.ok(proyectoService.getProyectosVisibles());
    }

    @GetMapping("/servicio/{servicioId}")
    public ResponseEntity<List<Proyecto>> getProyectosByServicioId(@PathVariable Integer servicioId) {
        return ResponseEntity.ok(proyectoService.getProyectosByServicioId(servicioId));
    }

    @GetMapping("/prioridad")
    public ResponseEntity<List<Proyecto>> getProyectosOrdenadosPorPrioridad() {
        return ResponseEntity.ok(proyectoService.getProyectosOrdenadosPorPrioridad());
    }
}
