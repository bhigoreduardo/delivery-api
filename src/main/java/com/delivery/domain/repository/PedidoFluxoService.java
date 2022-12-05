package com.delivery.domain.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.domain.model.Pedido;
import com.delivery.domain.service.PedidoService;

@Service
public class PedidoFluxoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Transactional
	public void updateConfirmar(String codigoPedido) {
		Pedido pedido = pedidoService.findByCodigo(codigoPedido);

		if (pedido.isAllowConfirmar()) {
			pedido.setStatusConfirmar();
			pedidoRepository.save(pedido);
		}

	}

	@Transactional
	public void updateEntregar(String codigoProduto) {
		Pedido pedido = pedidoService.findByCodigo(codigoProduto);

		if (pedido.isAllowEntregar()) {
			pedido.setStatusEntregar();
			pedidoRepository.save(pedido);
		}

	}

	@Transactional
	public void updateCancelar(String codigoProduto) {
		Pedido pedido = pedidoService.findByCodigo(codigoProduto);

		if (pedido.isAllowCancelar()) {
			pedido.setStatusCancelar();
			pedidoRepository.save(pedido);
		}

	}

}
