package com.delivery.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.NegocioException;
import com.delivery.domain.exception.PedidoNaoEncontradoException;
import com.delivery.domain.model.Cidade;
import com.delivery.domain.model.FormaPagamento;
import com.delivery.domain.model.Pedido;
import com.delivery.domain.model.Produto;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.model.Usuario;
import com.delivery.domain.repository.ItemPedidoRepository;
import com.delivery.domain.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private FormaPagamentoService formaPagamentoService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido findByCodigo(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido save(Pedido pedido) {

		validatePedido(pedido);
		pedidoRepository.flush();
		validateItens(pedido);
		pedidoRepository.flush();

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calculateValorTotal();

		pedido = pedidoRepository.save(pedido);
		saveItensPedido(pedido);
		
		return pedido;
	}

	private void validatePedido(Pedido pedido) {
		FormaPagamento formaPagamento = formaPagamentoService.findById(pedido.getFormaPagamento().getId());
		Restaurante restaurante = restauranteService.findById(pedido.getRestaurante().getId());
		Cidade cidade = cidadeService.findById(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = usuarioService.findById(pedido.getCliente().getId());

		pedido.setFormaPagamento(formaPagamento);
		pedido.setRestaurante(restaurante);
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);

		if (restaurante.isNotAllowFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento %s não é aceita por este restaurante.",
					formaPagamento.getDescricao()));
		}
	}

	private void validateItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.findById(item.getProduto().getId(), pedido.getRestaurante().getId());

			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	private void saveItensPedido(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			item.setPedido(pedido);
			itemPedidoRepository.save(item);
		});
	}

}
