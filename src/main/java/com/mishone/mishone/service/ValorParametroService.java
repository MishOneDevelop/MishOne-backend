package com.mishone.mishone.service;

import com.mishone.mishone.model.ValorParametro;

import java.util.List;
import java.util.Optional;

public interface ValorParametroService {

    List<ValorParametro> getAllValoresParametros();

    List<ValorParametro> getValoresByParametroId(Integer parametroId);

    Optional<ValorParametro> getValorParametroById(Integer id);

    ValorParametro createValorParametro(ValorParametro valorParametro);

    ValorParametro updateValorParametro(Integer id, ValorParametro valorParametroDetalles);

    void deleteValorParametro(Integer id);
}

