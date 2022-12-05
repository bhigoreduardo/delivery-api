package com.delivery.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;
	
	@CreationTimestamp
	//@Column(nullable = false)
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	//@Column(nullable = false)
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_formapagamento", 
		joinColumns = @JoinColumn(name = "id_restaurante"),
		inverseJoinColumns = @JoinColumn(name = "id_formapagamento"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>(); 
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario",
			joinColumns = @JoinColumn(name = "id_restaurante"),
			inverseJoinColumns = @JoinColumn(name = "id_usuario"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	@Column(nullable = false)
	private Boolean aberto = Boolean.FALSE;
	
	public void open() {
	    setAberto(true);
	}

	public void close() {
	    setAberto(false);
	}
	
	public void active() {
		setAtivo(true);
	}
	
	public void inactive() {
		setAtivo(false);
	}
	
	public boolean removeFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}

	public boolean addFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public boolean removeResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}
	
	public boolean addResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().contains(formaPagamento);
	}
	
	public boolean isNotAllowFormaPagamento(FormaPagamento formaPagamento) {
		return !aceitaFormaPagamento(formaPagamento);
	}
	
	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public boolean isInativo() {
		return !isAtivo();
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public boolean aberturaPermitida() {
		return isAtivo() && isFechado();
	}

	public boolean ativacaoPermitida() {
		return isInativo();
	}

	public boolean inativacaoPermitida() {
		return isAtivo();
	}

	public boolean fechamentoPermitido() {
		return isAberto() && isAtivo();
	}

}
