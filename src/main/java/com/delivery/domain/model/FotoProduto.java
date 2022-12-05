package com.delivery.domain.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@MapsId
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    private Produto produto;
	
	private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    public Long getRestauranteId() {
        if (getProduto() != null) {
            return getProduto().getRestaurante().getId();
        }
        return null;
    }

}
