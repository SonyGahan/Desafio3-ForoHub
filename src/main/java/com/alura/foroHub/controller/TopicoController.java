package com.alura.foroHub.controller;

import com.alura.foroHub.dto.TopicoRequest;
import com.alura.foroHub.dto.TopicoResponse;
import com.alura.foroHub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<TopicoResponse> crearTopico(@RequestBody @Validated TopicoRequest request) {
        TopicoResponse response = topicoService.crearTopico(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public List<TopicoResponse> listarTopicos() {
        return topicoService.listarTopicos();
    }

    @GetMapping("/{id}")
    public TopicoResponse obtenerTopico(@PathVariable Long id) {
        return topicoService.obtenerTopico(id);
    }

    @PutMapping("/{id}")
    public TopicoResponse actualizarTopico(@PathVariable Long id, @RequestBody TopicoRequest request) {
        return topicoService.actualizarTopico(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
    }
}

