package com.alura.foroHub.dto;

import java.time.LocalDateTime;

public record TopicoResponse(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String estado,
        String autorNombre,
        String cursoNombre
) {}


