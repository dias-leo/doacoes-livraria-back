package br.unilasalle.livraria_doacoes.controller;

import br.unilasalle.livraria_doacoes.model.Livro;
import br.unilasalle.livraria_doacoes.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody Livro livro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.criar(livro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        return ResponseEntity.ok(livroService.atualizar(id, livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Livro>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(livroService.buscarPorStatus(status));
    }
}

