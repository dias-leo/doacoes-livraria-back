package br.unilasalle.livraria_doacoes.service;

import br.unilasalle.livraria_doacoes.exception.ResourceNotFoundException;
import br.unilasalle.livraria_doacoes.model.Categoria;
import br.unilasalle.livraria_doacoes.model.Livro;
import br.unilasalle.livraria_doacoes.model.Usuario;
import br.unilasalle.livraria_doacoes.repository.CategoriaRepository;
import br.unilasalle.livraria_doacoes.repository.LivroRepository;
import br.unilasalle.livraria_doacoes.repository.UsuarioRepository;
import br.unilasalle.livraria_doacoes.model.LivroStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado com id " + id));
    }

    public Livro criar(Livro livro) {
        livro.setIdLivro(null);
        Usuario usuario = usuarioRepository.findById(livro.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com id " + livro.getUsuario().getIdUsuario()));
        Categoria categoria = categoriaRepository.findById(livro.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada com id " + livro.getCategoria().getIdCategoria()));

        livro.setUsuario(usuario);
        livro.setCategoria(categoria);
        if (livro.getStatus() == null) {
            livro.setStatus(LivroStatus.DISPONIVEL);
        }
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro dados) {
        Livro existente = buscarPorId(id);
        existente.setTitulo(dados.getTitulo());
        existente.setAutor(dados.getAutor());
        existente.setDescricao(dados.getDescricao());
        if (dados.getStatus() != null) {
            existente.setStatus(dados.getStatus());
        }

        if (dados.getUsuario() != null && dados.getUsuario().getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dados.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com id " + dados.getUsuario().getIdUsuario()));
            existente.setUsuario(usuario);
        }

        if (dados.getCategoria() != null && dados.getCategoria().getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dados.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada com id " + dados.getCategoria().getIdCategoria()));
            existente.setCategoria(categoria);
        }

        return livroRepository.save(existente);
    }

    public void deletar(Long id) {
        Livro existente = buscarPorId(id);
        livroRepository.delete(existente);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> buscarPorStatus(String status) {
        try {
            LivroStatus s = LivroStatus.valueOf(status.toUpperCase());
            return livroRepository.findByStatus(s);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

    public List<Livro> listarAcervoDisponivel() {
        return livroRepository.findByStatus(LivroStatus.DISPONIVEL);
    }
}


