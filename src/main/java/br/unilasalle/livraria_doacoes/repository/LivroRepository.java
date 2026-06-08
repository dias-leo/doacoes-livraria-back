package br.unilasalle.livraria_doacoes.repository;

import br.unilasalle.livraria_doacoes.model.Livro;
import br.unilasalle.livraria_doacoes.model.LivroStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByStatus(LivroStatus status);
}


