package br.unilasalle.livraria_doacoes.repository;

import br.unilasalle.livraria_doacoes.model.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}

