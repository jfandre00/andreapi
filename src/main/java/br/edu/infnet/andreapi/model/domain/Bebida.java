package br.edu.infnet.andreapi.model.domain;

import java.math.BigDecimal;

public class Bebida extends Produto {
	
	private int tamanhoMl; // Atributo específico de Bebida
	private boolean alcoolica;
	
	public Bebida(String nome, BigDecimal preco, int estoque, Categoria categoria, int tamanhoMl, boolean alcoolica) {
		
		// AT - Uso do super() para chamar o construtor da mãe
		super(nome, preco, estoque, categoria); 
		this.tamanhoMl = tamanhoMl;
		this.alcoolica = alcoolica;
	}
	
	@Override
	public String toString() {
		// Vamos reutilizar o toString() da mãe e adicionar os específicos de Bebida
		// usei um ternário para exibir se é alcoólica ou não
		return super.toString() + " | Tamanho: " + tamanhoMl + "ml" + (alcoolica ? " (Alcoólica)" : "");
	}

	public int getTamanhoMl() {
		return tamanhoMl;
	}

	public void setTamanhoMl(int tamanhoMl) {
		this.tamanhoMl = tamanhoMl;
	}

	public boolean isAlcoolica() {
		return alcoolica;
	}

	public void setAlcoolica(boolean alcoolica) {
		this.alcoolica = alcoolica;
	}
	
	
}
