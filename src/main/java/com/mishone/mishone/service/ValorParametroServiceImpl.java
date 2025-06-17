package com.mishone.mishone.service;

import com.mishone.mishone.model.ValorParametro;
import com.mishone.mishone.repository.ValorParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValorParametroServiceImpl implements ValorParametroService {

    @Autowired
    private ValorParametroRepository valorParametroRepository;

    @Override
    public List<ValorParametro> getAllValoresParametros() {
        return valorParametroRepository.findAll();
    }

    @Override
    public List<ValorParametro> getValoresByParametroId(Integer parametroId) {
        return valorParametroRepository.findByParametro_IdParametro(parametroId);
    }

    @Override
    public Optional<ValorParametro> getValorParametroById(Integer id) {
        return valorParametroRepository.findById(id);
    }

    @Override
    public ValorParametro createValorParametro(ValorParametro valorParametro) {
        return valorParametroRepository.save(valorParametro);
    }

    @Override
    public ValorParametro updateValorParametro(Integer id, ValorParametro valorParametroDetalles) {
        ValorParametro valorParametro = valorParametroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El valor del parámetro con ID " + id + " no existe"));

        valorParametro.setValor(valorParametroDetalles.getValor());
        valorParametro.setDescripcion(valorParametroDetalles.getDescripcion());
        valorParametro.setActivo(valorParametroDetalles.getActivo());
        valorParametro.setPrioridad(valorParametroDetalles.getPrioridad());

        return valorParametroRepository.save(valorParametro);
    }

    @Override
    public void deleteValorParametro(Integer id) {
        if (!valorParametroRepository.existsById(id)) {
            throw new IllegalArgumentException("El valor del parámetro con ID " + id + " no existe");
        }
        valorParametroRepository.deleteById(id);
    }
}
