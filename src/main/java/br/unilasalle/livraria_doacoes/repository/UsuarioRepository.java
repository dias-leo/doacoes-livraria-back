package br.unilasalle.livraria_doacoes.repository;

import br.unilasalle.livraria_doacoes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmailAndCpf(String email, String cpf);

    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.livros")
    List<Usuario> findAllWithLivros();

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.livros WHERE u.idUsuario = :id")
    Optional<Usuario> findByIdWithLivros(@Param("id") Long id);
}
