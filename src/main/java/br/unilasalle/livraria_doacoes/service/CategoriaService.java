package br.unilasalle.livraria_doacoes.service;

import br.unilasalle.livraria_doacoes.exception.ResourceNotFoundException;
import br.unilasalle.livraria_doacoes.model.Categoria;
import br.unilasalle.livraria_doacoes.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria nao encontrada com id " + id));
    }

    public Categoria criar(Categoria categoria) {
        categoria.setIdCategoria(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria dados) {
        Categoria existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        return categoriaRepository.save(existente);
    }

    public void deletar(Long id) {
        Categoria existente = buscarPorId(id);
        categoriaRepository.delete(existente);
    }
}

