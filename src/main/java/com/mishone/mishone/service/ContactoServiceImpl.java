package com.mishone.mishone.service;

import com.mishone.mishone.model.Contacto;
import com.mishone.mishone.model.ValorParametro;
import com.mishone.mishone.repository.ContactoRepository;
import com.mishone.mishone.repository.ValorParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactoServiceImpl implements ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private ValorParametroRepository valorParametroRepository;

    @Override
    public List<Contacto> getAllContactos() {
        return contactoRepository.findAll();
    }

    @Override
    public Optional<Contacto> getContactoById(Integer id) {
        return contactoRepository.findById(id);
    }

    @Override
    public Contacto createContacto(Contacto contacto) {
        ValorParametro estadoPendiente = valorParametroRepository.findByValor("Pendiente")
                .orElseThrow(() -> new IllegalArgumentException("Estado no válido"));
        contacto.setEstado(estadoPendiente);
        return contactoRepository.save(contacto);
    }


    @Override
    public Contacto updateContacto(Integer id, Contacto contactoDetalles) {
        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El contacto con ID " + id + " no existe"));

        contacto.setNombre(contactoDetalles.getNombre());
        contacto.setEmail(contactoDetalles.getEmail());
        contacto.setTelefono(contactoDetalles.getTelefono());
        contacto.setMensaje(contactoDetalles.getMensaje());

        if (contactoDetalles.getEstado() != null) {
            ValorParametro estado = valorParametroRepository.findById(contactoDetalles.getEstado().getIdValorParametro())
                    .orElseThrow(() -> new IllegalArgumentException("Estado no válido"));
            contacto.setEstado(estado);
        }

        return contactoRepository.save(contacto);
    }

    @Override
    public void deleteContacto(Integer id) {
        if (!contactoRepository.existsById(id)) {
            throw new IllegalArgumentException("El contacto con ID " + id + " no existe");
        }
        contactoRepository.deleteById(id);
    }

    @Override
    public List<Contacto> getContactosByEstado(Integer idEstado) {
        return contactoRepository.findByEstado_IdValorParametro(idEstado);
    }

    @Override
    public List<Contacto> getContactosByFecha(LocalDateTime inicio, LocalDateTime fin) {
        return contactoRepository.findByFechaEnvioBetween(inicio, fin);
    }
}
