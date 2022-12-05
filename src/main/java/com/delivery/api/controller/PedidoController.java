package com.delivery.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.PedidoInputDisassembler;
import com.delivery.api.assembler.PedidoModelAssembler;
import com.delivery.api.model.PedidoModel;
import com.delivery.api.model.input.PedidoInput;
import com.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.delivery.domain.exception.NegocioException;
import com.delivery.domain.model.Pedido;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.model.Usuario;
import com.delivery.domain.repository.PedidoRepository;
import com.delivery.domain.service.PedidoService;
import com.delivery.domain.service.RestauranteService;
import com.delivery.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@GetMapping("/{codigoPedido}")
	public PedidoModel findByCodigo(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.findByCodigo(codigoPedido);

		return pedidoModelAssembler.toModel(pedido);
	}

	@GetMapping("/restaurantes/{restauranteId}")
	public Page<PedidoModel> findAllRestaurante(@PathVariable Long restauranteId,
			@PageableDefault(size = 5) Pageable pageable) {
		Restaurante restaurante = restauranteService.findById(restauranteId);

		Page<Pedido> pedidosPage = pedidoRepository.findByRestaurante(restaurante, pageable);
		Page<PedidoModel> pedidosModelPage = pedidoModelAssembler.toPageModel(pedidosPage);

		return pedidosModelPage;
	}

	@GetMapping("/clientes/{clienteId}")
	public Page<PedidoModel> findAllCliente(@PathVariable Long clienteId, @PageableDefault(size = 5) Pageable pageable) {
		Usuario cliente = usuarioService.findById(clienteId);

		Page<Pedido> pedidosPage = pedidoRepository.findByCliente(cliente, pageable);
		Page<PedidoModel> pedidosModelPage = pedidoModelAssembler.toPageModel(pedidosPage);

		return pedidosModelPage;
	}

	@PostMapping
	public PedidoModel insert(@RequestBody @Valid PedidoInput pedidoInput, @RequestParam Long clienteId) {
		try {
			Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			pedido.setCliente(new Usuario());
			pedido.getCliente().setId(clienteId);

			return pedidoModelAssembler.toModel(pedidoService.save(pedido));
		} catch (EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

}
