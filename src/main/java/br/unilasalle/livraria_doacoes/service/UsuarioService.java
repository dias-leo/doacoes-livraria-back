package br.unilasalle.livraria_doacoes.service;

import br.unilasalle.livraria_doacoes.exception.ResourceNotFoundException;
import br.unilasalle.livraria_doacoes.model.Usuario;
import br.unilasalle.livraria_doacoes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAllWithLivros();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findByIdWithLivros(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com id " + id));
    }

    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "Email ja cadastrado");
        }

        if (usuario.getCpf() != null && usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST, "CPF ja cadastrado");
        }

        usuario.setIdUsuario(null);
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String email, String cpf) {
        return usuarioRepository.findByEmailAndCpf(email, cpf)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Credenciais invalidas"));
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setTelefone(dados.getTelefone());
        existente.setCpf(dados.getCpf());
        return usuarioRepository.save(existente);
    }

    public void deletar(Long id) {
        Usuario existente = buscarPorId(id);
        usuarioRepository.delete(existente);
    }
}

