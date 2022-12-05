package com.delivery.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	
	default String factoryNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
	
	void remover(String nomeArquivo);
	
	default void substituir(String nomeAntigoArquivo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if (nomeAntigoArquivo != null) {
			this.remover(nomeAntigoArquivo);
		}
	}
	
	@Builder
	@Getter
	public class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
		private String contentType;
		private Long size;
		
	}

}
