package com.alura.foroHub.repository;

import com.alura.foroHub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca un usuario por su correo electrónico
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    // Verifica si existe un usuario con un correo electrónico dado
    boolean existsByCorreoElectronico(String correoElectronico);
}

