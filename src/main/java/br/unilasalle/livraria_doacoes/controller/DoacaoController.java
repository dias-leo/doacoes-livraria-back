package br.unilasalle.livraria_doacoes.controller;

import br.unilasalle.livraria_doacoes.model.Doacao;
import br.unilasalle.livraria_doacoes.service.DoacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doacoes")
@RequiredArgsConstructor
public class DoacaoController {

    private final DoacaoService doacaoService;

    @GetMapping
    public ResponseEntity<List<Doacao>> listarTodos() {
        return ResponseEntity.ok(doacaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doacao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(doacaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Doacao> criar(@RequestBody Doacao doacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doacaoService.criar(doacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doacao> atualizar(@PathVariable Long id, @RequestBody Doacao doacao) {
        return ResponseEntity.ok(doacaoService.atualizar(id, doacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        doacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

