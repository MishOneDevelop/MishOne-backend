package com.mishone.mishone.repository;

import com.mishone.mishone.model.DetalleServicio;
import com.mishone.mishone.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleServicioRepository extends JpaRepository<DetalleServicio, Integer> {

    // Buscar todos los detalles por servicio
    List<DetalleServicio> findByServicio(Servicio servicio);

    // Buscar todos los detalles activos
    List<DetalleServicio> findByActivoTrue();

    // Buscar por servicio y activo
    List<DetalleServicio> findByServicioAndActivoTrue(Servicio servicio);
}
