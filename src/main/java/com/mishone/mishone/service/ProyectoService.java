package com.mishone.mishone.service;

import com.mishone.mishone.model.Proyecto;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {

    List<Proyecto> getAllProyectos();

    Optional<Proyecto> getProyectoById(Integer id);

    Proyecto createProyecto(Proyecto proyecto);

    Proyecto updateProyecto(Integer id, Proyecto proyectoDetalles);

    void deleteProyecto(Integer id);

    List<Proyecto> getProyectosVisibles();

    List<Proyecto> getProyectosByServicioId(Integer servicioId);

    List<Proyecto> getProyectosOrdenadosPorPrioridad();
}

