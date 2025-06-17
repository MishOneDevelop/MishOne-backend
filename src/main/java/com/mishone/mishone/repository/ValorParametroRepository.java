package com.mishone.mishone.repository;

import com.mishone.mishone.model.ValorParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValorParametroRepository extends JpaRepository<ValorParametro, Integer> {
    // Buscar valores de un parámetro específico
    List<ValorParametro> findByParametro_IdParametro(Integer idParametro);

    // Buscar valores activos
    List<ValorParametro> findByActivoTrue();

    Optional<ValorParametro> findByValor(String valor);
}