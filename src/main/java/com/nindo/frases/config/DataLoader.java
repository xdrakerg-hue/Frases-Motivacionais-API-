package com.nindo.frases.config;

import com.nindo.frases.model.Frase;
import com.nindo.frases.repository.FraseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final FraseRepository repository;

    public DataLoader(FraseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        List<Frase> seed = List.of(
                new Frase("A persistencia realiza o impossivel.", "Proverbio Chines", "motivacao"),
                new Frase("O sucesso e a soma de pequenos esforcos repetidos dia apos dia.", "Robert Collier", "motivacao"),
                new Frase("Acredite em si proprio e chegara um dia em que os outros nao terao outra escolha senao acreditar com voce.", "Cynthia Kersey", "autoestima"),
                new Frase("A vida e 10% o que acontece com voce e 90% como voce reage a isso.", "Charles R. Swindoll", "vida"),
                new Frase("Nao espere por uma crise para descobrir o que e importante em sua vida.", "Platao", "sabedoria"),
                new Frase("O unico lugar onde o sucesso vem antes do trabalho e no dicionario.", "Albert Einstein", "trabalho"),
                new Frase("Voce nunca e velho demais para estabelecer outro objetivo ou para sonhar um novo sonho.", "C. S. Lewis", "sonhos"),
                new Frase("A felicidade nao e algo pronto. Ela vem de suas proprias acoes.", "Dalai Lama", "felicidade"),
                new Frase("O que voce faz hoje pode melhorar todos os seus amanhas.", "Ralph Marston", "motivacao"),
                new Frase("A coragem e a primeira das qualidades humanas porque garante todas as outras.", "Aristoteles", "coragem")
        );

        repository.saveAll(seed);
    }
}
