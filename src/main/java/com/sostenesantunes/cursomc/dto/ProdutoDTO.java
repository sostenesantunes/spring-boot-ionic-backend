package com.sostenesantunes.cursomc.dto;

import java.io.Serializable;

import com.sostenesantunes.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private Integer id;
	private String nome;
	private Double preco;
	
	public ProdutoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	

	public ProdutoDTO(Produto obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.preco = obj.getPreco();
	}
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
}
