package br.unilasalle.livraria_doacoes.service;

import br.unilasalle.livraria_doacoes.dto.DoacaoSimplesRequest;
import br.unilasalle.livraria_doacoes.exception.ResourceNotFoundException;
import br.unilasalle.livraria_doacoes.model.Categoria;
import br.unilasalle.livraria_doacoes.model.Doacao;
import br.unilasalle.livraria_doacoes.model.Livro;
import br.unilasalle.livraria_doacoes.model.LivroStatus;
import br.unilasalle.livraria_doacoes.model.Usuario;
import br.unilasalle.livraria_doacoes.repository.CategoriaRepository;
import br.unilasalle.livraria_doacoes.repository.DoacaoRepository;
import br.unilasalle.livraria_doacoes.repository.LivroRepository;
import br.unilasalle.livraria_doacoes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Doacao> listarTodos() {
        return doacaoRepository.findAll();
    }

    public Doacao buscarPorId(Long id) {
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doacao nao encontrada com id " + id));
    }

    public Doacao criar(DoacaoSimplesRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseGet(() -> usuarioRepository.save(Usuario.builder()
                        .nome(request.nome())
                        .email(request.email())
                        .telefone(request.telefone())
                        .build()));

        Categoria categoria = categoriaRepository.findByNomeIgnoreCase("Geral")
                .orElseGet(() -> categoriaRepository.save(Categoria.builder().nome("Geral").build()));

        Livro livro = livroRepository.save(Livro.builder()
                .titulo(request.titulo())
                .autor(request.nome())
                .descricao("Livro doado pelo formulario simples")
                .status(LivroStatus.DISPONIVEL)
                .usuario(usuario)
                .categoria(categoria)
                .build());

        Doacao doacao = Doacao.builder()
                .dataDoacao(LocalDate.now())
                .nomeRecebedor(request.nome())
                .contatoRecebedor(request.telefone() + " | " + request.email())
                .livro(livro)
                .build();

        return doacaoRepository.save(doacao);
    }

    public Doacao atualizar(Long id, Doacao dados) {
        Doacao existente = buscarPorId(id);
        existente.setDataDoacao(dados.getDataDoacao());
        existente.setNomeRecebedor(dados.getNomeRecebedor());
        existente.setContatoRecebedor(dados.getContatoRecebedor());
        return doacaoRepository.save(existente);
    }

    public void deletar(Long id) {
        Doacao existente = buscarPorId(id);
        Livro livro = existente.getLivro();
        if (livro != null) {
            livro.setStatus(LivroStatus.DISPONIVEL);
        }
        doacaoRepository.delete(existente);
    }
}


