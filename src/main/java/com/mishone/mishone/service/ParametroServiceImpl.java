package com.mishone.mishone.service;

import com.mishone.mishone.model.Parametro;
import com.mishone.mishone.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public List<Parametro> getAllParametros() {
        return parametroRepository.findAll();
    }

    @Override
    public Optional<Parametro> getParametroById(Integer id) {
        return parametroRepository.findById(id);
    }

    @Override
    public Parametro createParametro(Parametro parametro) {
        return parametroRepository.save(parametro);
    }

    @Override
    public Parametro updateParametro(Integer id, Parametro parametroDetalles) {
        Parametro parametro = parametroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El parámetro con ID " + id + " no existe"));

        parametro.setNombre(parametroDetalles.getNombre());
        parametro.setDescripcion(parametroDetalles.getDescripcion());
        parametro.setActivo(parametroDetalles.getActivo());

        return parametroRepository.save(parametro);
    }

    @Override
    public void deleteParametro(Integer id) {
        if (!parametroRepository.existsById(id)) {
            throw new IllegalArgumentException("El parámetro con ID " + id + " no existe");
        }
        parametroRepository.deleteById(id);
    }
}
