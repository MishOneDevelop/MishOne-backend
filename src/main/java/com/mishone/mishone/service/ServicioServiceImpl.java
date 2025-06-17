package com.mishone.mishone.service;

import com.mishone.mishone.model.Servicio;
import com.mishone.mishone.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Optional<Servicio> getServicioById(Integer id) {
        return servicioRepository.findById(id);
    }

    @Override
    public Servicio createServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio updateServicio(Integer id, Servicio servicioDetalles) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El servicio con ID " + id + " no existe"));

        servicio.setNombre(servicioDetalles.getNombre());
        servicio.setDescripcionMin(servicioDetalles.getDescripcionMin());
        servicio.setDescripcionMax(servicioDetalles.getDescripcionMax());
        servicio.setPrecioMin(servicioDetalles.getPrecioMin());
        servicio.setPrecioMax(servicioDetalles.getPrecioMax());
        servicio.setCategoria(servicioDetalles.getCategoria());
        servicio.setDuracionEstimada(servicioDetalles.getDuracionEstimada());
        servicio.setActivo(servicioDetalles.getActivo());

        return servicioRepository.save(servicio);
    }


    @Override
    public void deleteServicio(Integer id) {
        if (!servicioRepository.existsById(id)) {
            throw new IllegalArgumentException("El servicio con ID " + id + " no existe");
        }
        servicioRepository.deleteById(id);
    }

    @Override
    public List<Servicio> getServiciosActivos() {
        return servicioRepository.findByActivoTrue();
    }
}

