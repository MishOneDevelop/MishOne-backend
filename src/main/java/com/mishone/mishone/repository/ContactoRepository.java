package com.mishone.mishone.repository;

import com.mishone.mishone.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    // Buscar contactos por estado
    List<Contacto> findByEstado_IdValorParametro(Integer idEstado);

    // Buscar contactos por fecha de envío
    List<Contacto> findByFechaEnvioBetween(LocalDateTime inicio, LocalDateTime fin);
}