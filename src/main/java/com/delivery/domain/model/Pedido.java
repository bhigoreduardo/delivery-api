package com.delivery.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.delivery.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	@Column(nullable = false)
	private BigDecimal subtotal;

	@Column(nullable = false)
	private BigDecimal taxaFrete;

	@Column(nullable = false)
	private BigDecimal valorTotal;

	@CreationTimestamp
	@Column(nullable = false)
	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;

	private OffsetDateTime dataCancelamento;

	private OffsetDateTime dataEntrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusPedido status = StatusPedido.CRIADO;

	@Embedded
	private Endereco enderecoEntrega;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();

	public void calculateValorTotal() {
		getItens().forEach(ItemPedido::calculatePrecoTotal);

		this.subtotal = getItens().stream().map(item -> item.getPrecoTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void setStatusConfirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
	}

	public void setStatusEntregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}

	public void setStatusCancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
	}

	public boolean isAllowConfirmar() {
		return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
	}

	public boolean isAllowEntregar() {
		return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
	}

	public boolean isAllowCancelar() {
		return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
	}

	private void setStatus(StatusPedido novoStatus) {
		if (getStatus().naoPodeAlterarPara(novoStatus)) {

			throw new NegocioException(String.format("Status do pedido %s não não pode ser alterado de %s para %s",
					getCodigo(), getStatus().getDecricao(), novoStatus.getDecricao()));
		}

		this.status = novoStatus;
	}

	@PrePersist
	private void generateCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

}
