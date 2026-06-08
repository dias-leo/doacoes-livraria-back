package br.unilasalle.livraria_doacoes.service;

import br.unilasalle.livraria_doacoes.exception.ResourceNotFoundException;
import br.unilasalle.livraria_doacoes.model.Usuario;
import br.unilasalle.livraria_doacoes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com id " + id));
    }

    public Usuario criar(Usuario usuario) {
        usuario.setIdUsuario(null);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setTelefone(dados.getTelefone());
        return usuarioRepository.save(existente);
    }

    public void deletar(Long id) {
        Usuario existente = buscarPorId(id);
        usuarioRepository.delete(existente);
    }
}

