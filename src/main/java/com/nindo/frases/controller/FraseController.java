package com.nindo.frases.controller;

import com.nindo.frases.model.Frase;
import com.nindo.frases.service.FraseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frases")
public class FraseController {

    private final FraseService service;

    public FraseController(FraseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Frase> listar(@RequestParam(value = "categoria", required = false) String categoria) {
        if (categoria != null && !categoria.isBlank()) {
            return service.listarPorCategoria(categoria);
        }
        return service.listarTodas();
    }

    @GetMapping("/random")
    public Frase aleatoria() {
        return service.buscarAleatoria();
    }

    @GetMapping("/{id}")
    public Frase buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Frase> criar(@Valid @RequestBody Frase frase) {
        Frase salva = service.salvar(frase);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PostMapping("/{id}/like")
    public Frase curtir(@PathVariable Long id) {
        return service.curtir(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
