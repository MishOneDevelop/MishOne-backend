package com.mishone.mishone.repository;


import com.mishone.mishone.model.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
    // Puedes agregar métodos personalizados si es necesario, por ejemplo:
    Parametro findByNombre(String nombre);
}