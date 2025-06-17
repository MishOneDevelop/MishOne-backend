package com.mishone.mishone.service;

import com.mishone.mishone.model.DetalleServicio;
import com.mishone.mishone.model.Servicio;
import com.mishone.mishone.repository.DetalleServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleServicioServiceImpl implements DetalleServicioService {

    @Autowired
    private DetalleServicioRepository detalleServicioRepository;

    @Override
    public List<DetalleServicio> getAllDetalleServicios() {
        return detalleServicioRepository.findAll();
    }

    @Override
    public Optional<DetalleServicio> getDetalleServicioById(Integer id) {
        return detalleServicioRepository.findById(id);
    }

    @Override
    public List<DetalleServicio> getDetalleServiciosByServicio(Servicio servicio) {
        return detalleServicioRepository.findByServicio(servicio);
    }

    @Override
    public DetalleServicio createDetalleServicio(DetalleServicio detalle) {
        return detalleServicioRepository.save(detalle);
    }

    @Override
    public DetalleServicio updateDetalleServicio(Integer id, DetalleServicio detalleActualizado) {
        DetalleServicio detalle = detalleServicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El detalle con ID " + id + " no existe"));

        detalle.setTitulo(detalleActualizado.getTitulo());
        detalle.setDescripcion(detalleActualizado.getDescripcion());
        detalle.setTecnologias(detalleActualizado.getTecnologias());
        detalle.setDuracion(detalleActualizado.getDuracion());
        detalle.setModalidad(detalleActualizado.getModalidad());
        detalle.setValor(detalleActualizado.getValor());
        detalle.setActivo(detalleActualizado.getActivo());
        detalle.setServicio(detalleActualizado.getServicio());

        return detalleServicioRepository.save(detalle);
    }

    @Override
    public void deleteDetalleServicio(Integer id) {
        if (!detalleServicioRepository.existsById(id)) {
            throw new IllegalArgumentException("El detalle con ID " + id + " no existe");
        }
        detalleServicioRepository.deleteById(id);
    }

    @Override
    public List<DetalleServicio> getDetalleServiciosActivos() {
        return detalleServicioRepository.findByActivoTrue();
    }

    @Override
    public List<DetalleServicio> getDetalleServiciosActivosByServicio(Servicio servicio) {
        return detalleServicioRepository.findByServicioAndActivoTrue(servicio);
    }
}
