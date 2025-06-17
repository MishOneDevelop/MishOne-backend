package com.mishone.mishone.repository;


import com.mishone.mishone.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    // Buscar proyectos visibles
    List<Proyecto> findByVisibleTrue();

    // Buscar proyectos por servicio
    List<Proyecto> findByServicio_IdServicio(Integer idServicio);

    // Ordenar proyectos por prioridad
    List<Proyecto> findByOrderByPrioridadAsc();
}