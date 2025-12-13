package br.edu.infnet.andreapi.model.domain;

import java.math.BigDecimal;

public class Comida extends Produto{
	
	private float pesoKg; // Atributo específico de Comida
	private boolean organico;
	
	public Comida(String nome, BigDecimal preco, int estoque, Categoria categoria, float pesoKg, boolean organico) {
		
		// AT - Uso do super() para chamar o construtor da mãe
		super(nome, preco, estoque, categoria); 
		this.pesoKg = pesoKg;
		this.organico = organico;
	}
	
	@Override
	public String toString() {
		// Vamos reutilizar o toString() da mãe e adicionar os específicos de Comida
		// usei um ternário para exibir se é orgânico ou não
		return super.toString() + " | Peso: " + pesoKg + "kg" + (organico ? " (Orgânico)" : "");
	}

	public float getPesoKg() {
		return pesoKg;
	}

	public void setPesoKg(float pesoKg) {
		this.pesoKg = pesoKg;
	}

	public boolean isOrganico() {
		return organico;
	}

	public void setOrganico(boolean organico) {
		this.organico = organico;
	}
	
	
	

}
