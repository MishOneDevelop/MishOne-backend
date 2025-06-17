package com.mishone.mishone.service;

import com.mishone.mishone.model.Proyecto;
import com.mishone.mishone.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }

    @Override
    public Optional<Proyecto> getProyectoById(Integer id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public Proyecto createProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    @Override
    public Proyecto updateProyecto(Integer id, Proyecto proyectoDetalles) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El proyecto con ID " + id + " no existe"));

        proyecto.setNombre(proyectoDetalles.getNombre());
        proyecto.setDescripcion(proyectoDetalles.getDescripcion());
        proyecto.setUrlImagen(proyectoDetalles.getUrlImagen());
        proyecto.setLinkDemo(proyectoDetalles.getLinkDemo());
        proyecto.setFechaPublicacion(proyectoDetalles.getFechaPublicacion());
        proyecto.setVisible(proyectoDetalles.getVisible());
        proyecto.setPrioridad(proyectoDetalles.getPrioridad());
        proyecto.setServicio(proyectoDetalles.getServicio());

        return proyectoRepository.save(proyecto);
    }

    @Override
    public void deleteProyecto(Integer id) {
        if (!proyectoRepository.existsById(id)) {
            throw new IllegalArgumentException("El proyecto con ID " + id + " no existe");
        }
        proyectoRepository.deleteById(id);
    }

    @Override
    public List<Proyecto> getProyectosVisibles() {
        return proyectoRepository.findByVisibleTrue();
    }

    @Override
    public List<Proyecto> getProyectosByServicioId(Integer servicioId) {
        return proyectoRepository.findByServicio_IdServicio(servicioId);
    }

    @Override
    public List<Proyecto> getProyectosOrdenadosPorPrioridad() {
        return proyectoRepository.findByOrderByPrioridadAsc();
    }
}

