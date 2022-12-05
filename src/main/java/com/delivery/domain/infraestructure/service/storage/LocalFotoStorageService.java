package com.delivery.domain.infraestructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.delivery.core.storage.StorageProperties;
import com.delivery.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception ex) {
			throw new StorageException("Não foi possível armazenar o arquivo.", ex);
		}
	}

	private Path getArquivoPath(String arquivoNome) {
		return Paths.get(storageProperties.getLocal().getDiretorioFotos().toString(), arquivoNome);
	}
	
	@Override
	public void remover(String nomeAntigoArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeAntigoArquivo);
			
			Files.deleteIfExists(arquivoPath);
		} catch(Exception ex) {
			throw new StorageException("Não foi possível armazenar o arquivo.", ex);
		}
	}

}
