package com.delivery.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.delivery.domain.infraestructure.service.storage.LocalFotoStorageService;
import com.delivery.domain.service.FotoStorageService;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	public FotoStorageService fotoStorageService() {
		return new LocalFotoStorageService();
	}

}
