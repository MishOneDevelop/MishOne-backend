package com.mishone.mishone.service;

import com.mishone.mishone.model.Contacto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContactoService {

    List<Contacto> getAllContactos();

    Optional<Contacto> getContactoById(Integer id);

    Contacto createContacto(Contacto contacto);

    Contacto updateContacto(Integer id, Contacto contactoDetalles);

    void deleteContacto(Integer id);

    List<Contacto> getContactosByEstado(Integer idEstado);

    List<Contacto> getContactosByFecha(LocalDateTime inicio, LocalDateTime fin);
}
