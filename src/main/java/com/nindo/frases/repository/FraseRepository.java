package com.nindo.frases.repository;

import com.nindo.frases.model.Frase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FraseRepository extends JpaRepository<Frase, Long> {

    List<Frase> findByCategoriaIgnoreCase(String categoria);
}
