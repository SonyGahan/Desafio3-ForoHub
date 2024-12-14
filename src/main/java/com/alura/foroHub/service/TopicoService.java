package com.alura.foroHub.service;

import com.alura.foroHub.dto.TopicoRequest;
import com.alura.foroHub.dto.TopicoResponse;
import com.alura.foroHub.exception.ResourceNotFoundException;
import com.alura.foroHub.model.Curso;
import com.alura.foroHub.model.Topico;
import com.alura.foroHub.model.Usuario;
import com.alura.foroHub.repository.CursoRepository;
import com.alura.foroHub.repository.TopicoRepository;
import com.alura.foroHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    //Crea un nuevo tópico en la base de datos.
    public TopicoResponse crearTopico(TopicoRequest request) {
        // Verificar si ya existe un tópico con el mismo título y mensaje
        if (topicoRepository.existsByTituloAndMensaje(request.titulo(), request.mensaje())) {
            throw new IllegalArgumentException("El tópico ya existe con el mismo título y mensaje.");
        }

        // Validar existencia del autor
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.autorId()));

        // Validar existencia del curso
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + request.cursoId()));

        // Crear y guardar el tópico
        Topico topico = Topico.builder()
                .titulo(request.titulo())
                .mensaje(request.mensaje())
                .estado(request.estado())
                .autor(autor)
                .curso(curso)
                .build();

        topicoRepository.save(topico);

        return convertirATopicoResponse(topico);
    }

    //Lista todos los tópicos existentes en la base de datos.
    public List<TopicoResponse> listarTopicos() {
        return topicoRepository.findAll()
                .stream()
                .map(this::convertirATopicoResponse)
                .collect(Collectors.toList());
    }

    //Obtiene los detalles de un tópico específico.
    public TopicoResponse obtenerTopico(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));

        return convertirATopicoResponse(topico);
    }

    //Actualiza los datos de un tópico existente.
    public TopicoResponse actualizarTopico(Long id, TopicoRequest request) {
        // Validar existencia del tópico
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado con ID: " + id));

        // Actualizar los campos
        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setEstado(request.estado());

        topicoRepository.save(topico);

        return convertirATopicoResponse(topico);
    }

    //Elimina un tópico de la base de datos.
    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tópico no encontrado con ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    //Convierte una entidad Topico en un objeto de respuesta TopicoResponse.
    private TopicoResponse convertirATopicoResponse(Topico topico) {
        return new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}


