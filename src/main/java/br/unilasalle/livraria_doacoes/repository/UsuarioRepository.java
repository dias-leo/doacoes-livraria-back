package br.unilasalle.livraria_doacoes.repository;

import br.unilasalle.livraria_doacoes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

