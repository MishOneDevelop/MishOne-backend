package com.mishone.mishone.controller;

import com.mishone.mishone.model.Parametro;
import com.mishone.mishone.service.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametros")
public class ParametroController {

    @Autowired
    private ParametroService parametroService;

    @GetMapping
    public ResponseEntity<List<Parametro>> getAllParametros() {
        return ResponseEntity.ok(parametroService.getAllParametros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parametro> getParametroById(@PathVariable Integer id) {
        return parametroService.getParametroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Parametro> createParametro(@RequestBody Parametro parametro) {
        return ResponseEntity.ok(parametroService.createParametro(parametro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parametro> updateParametro(@PathVariable Integer id, @RequestBody Parametro parametroDetalles) {
        return ResponseEntity.ok(parametroService.updateParametro(id, parametroDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParametro(@PathVariable Integer id) {
        parametroService.deleteParametro(id);
        return ResponseEntity.noContent().build();
    }
}
