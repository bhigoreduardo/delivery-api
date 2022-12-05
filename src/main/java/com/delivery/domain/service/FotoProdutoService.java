package com.delivery.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.FotoProdutoNaoEncontradoException;
import com.delivery.domain.model.FotoProduto;
import com.delivery.domain.repository.ProdutoRepository;
import com.delivery.domain.service.FotoStorageService.NovaFoto;

@Service
public class FotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto save(FotoProduto fotoProduto, InputStream dadosArquivo) {
		
		Long restauranteId = fotoProduto.getRestauranteId();
		Long produtoId = fotoProduto.getProduto().getId();
		String nomeNovoArquivo = fotoStorageService.factoryNomeArquivo(fotoProduto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoProdutoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if (fotoProdutoExistente.isPresent()) {
			nomeArquivoExistente = fotoProdutoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoProdutoExistente.get());
		}
		
		fotoProduto.setNomeArquivo(nomeNovoArquivo);
		fotoProduto = produtoRepository.save(fotoProduto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
								.nomeArquivo(fotoProduto.getNomeArquivo())
								.contentType(fotoProduto.getContentType())
								.size(fotoProduto.getTamanho())
								.inputStream(dadosArquivo)
								.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		return fotoProduto;
	}
	
	public FotoProduto findById(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(()-> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
	@Transactional
	public void deleteById(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = produtoRepository.findFotoById(restauranteId, produtoId).get();
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();
		
		fotoStorageService.remover(fotoProduto.getNomeArquivo());	
	}

}
