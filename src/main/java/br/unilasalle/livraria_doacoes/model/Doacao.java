package br.unilasalle.livraria_doacoes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "doacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_doacao")
	private Long idDoacao;

	@Column(name = "data_doacao", nullable = false)
	private LocalDate dataDoacao;

	@Column(name = "nome_recebedor", nullable = false)
	private String nomeRecebedor;

	@Column(name = "contato_recebedor", nullable = false)
	private String contatoRecebedor;

	@OneToOne(optional = false)
	@JoinColumn(name = "id_livro", nullable = false, unique = true)
	@JsonBackReference("livro-doacao")
	private Livro livro;
}

