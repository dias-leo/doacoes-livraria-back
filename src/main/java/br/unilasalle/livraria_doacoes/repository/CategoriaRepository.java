package br.unilasalle.livraria_doacoes.repository;

import br.unilasalle.livraria_doacoes.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	Optional<Categoria> findByNomeIgnoreCase(String nome);
}

