package com.mishone.mishone.controller;


import com.mishone.mishone.model.ValorParametro;
import com.mishone.mishone.service.ValorParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valores-parametros")
public class ValorParametroController {

    @Autowired
    private ValorParametroService valorParametroService;

    @GetMapping
    public ResponseEntity<List<ValorParametro>> getAllValoresParametros() {
        return ResponseEntity.ok(valorParametroService.getAllValoresParametros());
    }

    @GetMapping("/parametro/{parametroId}")
    public ResponseEntity<List<ValorParametro>> getValoresByParametroId(@PathVariable Integer parametroId) {
        return ResponseEntity.ok(valorParametroService.getValoresByParametroId(parametroId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValorParametro> getValorParametroById(@PathVariable Integer id) {
        return valorParametroService.getValorParametroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ValorParametro> createValorParametro(@RequestBody ValorParametro valorParametro) {
        return ResponseEntity.ok(valorParametroService.createValorParametro(valorParametro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValorParametro> updateValorParametro(@PathVariable Integer id, @RequestBody ValorParametro valorParametroDetalles) {
        return ResponseEntity.ok(valorParametroService.updateValorParametro(id, valorParametroDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValorParametro(@PathVariable Integer id) {
        valorParametroService.deleteValorParametro(id);
        return ResponseEntity.noContent().build();
    }
}
