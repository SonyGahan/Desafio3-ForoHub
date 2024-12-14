package com.alura.foroHub.repository;

import com.alura.foroHub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verifica si existe un tópico con un título y mensaje específicos
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    // Busca tópicos por el estado
    List<Topico> findByEstado(String estado);

    // Consulta personalizada: tópicos por curso y año de creación
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :cursoNombre AND FUNCTION('YEAR', t.fechaCreacion) = :anio")
    List<Topico> buscarPorCursoYAnio(@Param("cursoNombre") String cursoNombre, @Param("anio") int anio);
}


