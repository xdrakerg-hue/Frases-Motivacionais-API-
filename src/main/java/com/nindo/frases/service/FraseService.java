package com.nindo.frases.service;

import com.nindo.frases.exception.FraseNotFoundException;
import com.nindo.frases.model.Frase;
import com.nindo.frases.repository.FraseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FraseService {

    private final FraseRepository repository;

    public FraseService(FraseRepository repository) {
        this.repository = repository;
    }

    public List<Frase> listarTodas() {
        return repository.findAll();
    }

    public List<Frase> listarPorCategoria(String categoria) {
        return repository.findByCategoriaIgnoreCase(categoria);
    }

    public Frase buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FraseNotFoundException(id));
    }

    public Frase buscarAleatoria() {
        List<Frase> todas = repository.findAll();
        if (todas.isEmpty()) {
            throw new FraseNotFoundException("Nenhuma frase cadastrada.");
        }
        int indice = ThreadLocalRandom.current().nextInt(todas.size());
        return todas.get(indice);
    }

    public Frase salvar(Frase frase) {
        frase.setId(null);
        if (frase.getLikes() < 0) {
            frase.setLikes(0);
        }
        return repository.save(frase);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new FraseNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Frase curtir(Long id) {
        Frase frase = buscarPorId(id);
        frase.setLikes(frase.getLikes() + 1);
        return repository.save(frase);
    }
}
