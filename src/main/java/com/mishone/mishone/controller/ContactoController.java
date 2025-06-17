package com.mishone.mishone.controller;

import com.mishone.mishone.model.Contacto;
import com.mishone.mishone.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping
    public ResponseEntity<List<Contacto>> getAllContactos() {
        return ResponseEntity.ok(contactoService.getAllContactos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> getContactoById(@PathVariable Integer id) {
        return contactoService.getContactoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Contacto> createContacto(@RequestBody Contacto contacto) {
        return ResponseEntity.ok(contactoService.createContacto(contacto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> updateContacto(@PathVariable Integer id, @RequestBody Contacto contactoDetalles) {
        return ResponseEntity.ok(contactoService.updateContacto(id, contactoDetalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable Integer id) {
        contactoService.deleteContacto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<Contacto>> getContactosByEstado(@PathVariable Integer idEstado) {
        return ResponseEntity.ok(contactoService.getContactosByEstado(idEstado));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Contacto>> getContactosByFecha(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin) {
        return ResponseEntity.ok(contactoService.getContactosByFecha(inicio, fin));
    }
}