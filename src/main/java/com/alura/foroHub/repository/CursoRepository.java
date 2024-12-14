package com.alura.foroHub.repository;

import com.alura.foroHub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Busca un curso por su nombre
    Optional<Curso> findByNombre(String nombre);

    // Verifica si existe un curso con un nombre dado
    boolean existsByNombre(String nombre);
}

