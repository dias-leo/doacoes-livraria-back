package br.unilasalle.livraria_doacoes.service;

import br.unilasalle.livraria_doacoes.exception.ResourceNotFoundException;
import br.unilasalle.livraria_doacoes.model.Doacao;
import br.unilasalle.livraria_doacoes.model.Livro;
import br.unilasalle.livraria_doacoes.model.LivroStatus;
import br.unilasalle.livraria_doacoes.repository.DoacaoRepository;
import br.unilasalle.livraria_doacoes.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final LivroRepository livroRepository;

    public List<Doacao> listarTodos() {
        return doacaoRepository.findAll();
    }

    public Doacao buscarPorId(Long id) {
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doacao nao encontrada com id " + id));
    }

    public Doacao criar(Doacao doacao) {
        doacao.setIdDoacao(null);
        Livro livro = livroRepository.findById(doacao.getLivro().getIdLivro())
                .orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado com id " + doacao.getLivro().getIdLivro()));

        if (livro.getDoacao() != null) {
            throw new IllegalStateException("Livro ja possui uma doacao cadastrada");
        }

        livro.setStatus(LivroStatus.DOADO);
        doacao.setLivro(livro);
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


