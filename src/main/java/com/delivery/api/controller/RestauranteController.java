package com.delivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.RestauranteInputDisassembler;
import com.delivery.api.assembler.RestauranteModelAssembler;
import com.delivery.api.model.RestauranteModel;
import com.delivery.api.model.input.RestauranteInput;
import com.delivery.domain.exception.CidadeNaoEncontradaException;
import com.delivery.domain.exception.CozinhaNaoEncontradaException;
import com.delivery.domain.exception.NegocioException;
import com.delivery.domain.exception.RestauranteNaoEncontradoException;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.repository.RestauranteRepository;
import com.delivery.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public Page<RestauranteModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return restauranteModelAssembler.toPageModel(restauranteRepository.findAll(pageable));
	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel findById(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);

		return restauranteModelAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel insert(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

			restaurante = restauranteService.save(restaurante);

			return restauranteModelAssembler.toModel(restaurante);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@PutMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.OK)
	public RestauranteModel update(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restauranteCurrent = restauranteService.findById(restauranteId);

			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteCurrent);

			restauranteCurrent = restauranteService.save(restauranteCurrent);

			return restauranteModelAssembler.toModel(restauranteCurrent);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long restauranteId) {
		restauranteService.deleteById(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> active(@PathVariable Long restauranteId) {
        restauranteService.active(restauranteId);

        return ResponseEntity.noContent().build();
    }
	
	@DeleteMapping("/{restauranteId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactive(@PathVariable Long restauranteId) {
		restauranteService.inactive(restauranteId);

        return ResponseEntity.noContent().build();
    }
	
	@PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activeMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.active(restaurantesIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
	
	@DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactiveMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.inactive(restaurantesIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
	
	@PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> open(@PathVariable Long restauranteId) {
        restauranteService.open(restauranteId);

        return ResponseEntity.noContent().build();
    }
	
	@PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> close(@PathVariable Long restauranteId) {
        restauranteService.close(restauranteId);

        return ResponseEntity.noContent().build();
    }

}
