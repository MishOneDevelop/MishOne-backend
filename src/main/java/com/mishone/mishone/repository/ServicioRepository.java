package com.mishone.mishone.repository;


import com.mishone.mishone.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    // Buscar servicios por nombre
    Servicio findByNombre(String nombre);

    // Buscar servicios activos
    List<Servicio> findByActivoTrue();
}