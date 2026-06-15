package br.unilasalle.livraria_doacoes.controller;

import br.unilasalle.livraria_doacoes.model.Livro;
import br.unilasalle.livraria_doacoes.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/acervo")
@RequiredArgsConstructor
public class AcervoController {

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<List<Livro>> listarDisponiveis() {
        return ResponseEntity.ok(livroService.listarAcervoDisponivel());
    }
}

