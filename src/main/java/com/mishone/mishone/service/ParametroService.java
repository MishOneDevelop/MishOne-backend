package com.mishone.mishone.service;

import com.mishone.mishone.model.Parametro;

import java.util.List;
import java.util.Optional;

public interface ParametroService {

    List<Parametro> getAllParametros();

    Optional<Parametro> getParametroById(Integer id);

    Parametro createParametro(Parametro parametro);

    Parametro updateParametro(Integer id, Parametro parametroDetalles);

    void deleteParametro(Integer id);
}
