package com.mishone.mishone.service;

import com.mishone.mishone.model.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<Servicio> getAllServicios();

    Optional<Servicio> getServicioById(Integer id);

    Servicio createServicio(Servicio servicio);

    Servicio updateServicio(Integer id, Servicio servicioDetalles);

    void deleteServicio(Integer id);

    List<Servicio> getServiciosActivos();
}
