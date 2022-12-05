package com.delivery.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("delivery.storage")
public class StorageProperties {
	
	private Local local = new Local();
	private TipoStorage tipoStorage = TipoStorage.LOCAL;

	public enum TipoStorage {
		LOCAL
	}
	
	@Getter
	@Setter
	public class Local {
		private Path diretorioFotos;
	}

}
