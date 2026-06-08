package br.unilasalle.livraria_doacoes.repository;

import br.unilasalle.livraria_doacoes.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

