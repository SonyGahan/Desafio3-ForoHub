package com.sonygahan.API.foroHub.controller;

import com.sonygahan.API.foroHub.domain.respuesta.*;
import com.sonygahan.API.foroHub.domain.topico.Estado;
import com.sonygahan.API.foroHub.domain.topico.Topico;
import com.sonygahan.API.foroHub.domain.topico.TopicoRepository;
import com.sonygahan.API.foroHub.domain.usuario.Usuario;
import com.sonygahan.API.foroHub.domain.usuario.UsuarioRepository;
import com.sonygahan.API.foroHub.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping("/registrar")
    public ResponseEntity<DatosRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta dRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder){
        Usuario autor = usuarioRepository.findById(dRegistroRespuesta.usuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Topico topico = topicoRepository.findById(dRegistroRespuesta.topicoId()).orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        Respuesta respuesta = respuestaRepository.save(new Respuesta(dRegistroRespuesta, autor, topico));
        DatosRespuesta datosRespuesta = new DatosRespuesta(respuesta);
        URI url = uriComponentsBuilder.path("respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DatosListadoRespuesta>> listarRespuestas(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(respuestaRepository.findAll(pageable).map(DatosListadoRespuesta::new));
    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<DatosRespuesta> retornarRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        DatosRespuesta datosRespuesta = new DatosRespuesta(respuesta);
        return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("/marcar-solucion/{id}/{respuestaId}")
    @Transactional
    public ResponseEntity<DatosRespuesta> marcarSolucion(@PathVariable Long id, @PathVariable Long respuestaId) {
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado"));
        if (topico.getStatus() == Estado.SOLUCIONADO) {
            return ResponseEntity.badRequest().body(null);
        }
        Respuesta respuesta = respuestaRepository.findById(respuestaId).orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrada"));
        respuesta.setSolucion(true);
        topico.setStatus(Estado.SOLUCIONADO);
        topicoRepository.save(topico);
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok(new DatosRespuesta(respuesta));
    }
}
