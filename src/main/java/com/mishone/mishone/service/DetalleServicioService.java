package com.mishone.mishone.service;

import com.mishone.mishone.model.DetalleServicio;
import com.mishone.mishone.model.Servicio;

import java.util.List;
import java.util.Optional;

public interface DetalleServicioService {

    List<DetalleServicio> getAllDetalleServicios();

    Optional<DetalleServicio> getDetalleServicioById(Integer id);

    List<DetalleServicio> getDetalleServiciosByServicio(Servicio servicio);

    DetalleServicio createDetalleServicio(DetalleServicio detalle);

    DetalleServicio updateDetalleServicio(Integer id, DetalleServicio detalleActualizado);

    void deleteDetalleServicio(Integer id);

    List<DetalleServicio> getDetalleServiciosActivos();

    List<DetalleServicio> getDetalleServiciosActivosByServicio(Servicio servicio);
}
