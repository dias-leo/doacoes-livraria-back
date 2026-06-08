package br.unilasalle.livraria_doacoes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import br.unilasalle.livraria_doacoes.model.LivroStatus;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private Long idLivro;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LivroStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference("usuario-livros")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    @JsonBackReference("categoria-livros")
    private Categoria categoria;

    @OneToOne(mappedBy = "livro")
    @JsonManagedReference("livro-doacao")
    private Doacao doacao;
}


