package br.edu.infnet.andreapi.model.domain;

public class Produto {
	
	// Atributos essenciais
	
	public int estoque;
	public String nome;
	public boolean disponivel;
	public double preco;
	public String descricao;
	
	// Métodos Personalizados
	
	
	// Método público que aplica um desconto ao preço do produto. Ele chama um método privado para fazer o cálculo.
	public void aplicarDesconto(double percentual) {
		if (percentual > 0 && percentual < 100) {
			double novoPreco = calcularPrecoComDesconto(percentual);
			preco = novoPreco;
			System.out.println("Desconto de " + percentual + "% aplicado. Novo preço: R$ " + novoPreco);
		} else {
			System.out.println("Percentual de desconto inválido. O preço não foi alterado.");
		}
	}
	
	// Método privado que calcula o valor do produto com o desconto aplicado.
	private double calcularPrecoComDesconto(double percentual) {
		double valorDoDesconto = (preco * percentual) / 100;
		
		// vamos arredondar o valor do desconto para duas casas decimais
		
		
		return Math.round((preco - valorDoDesconto) * 100.0) / 100.0;
	}
	
	public void imprimirDetalhes() {
		String detalhes = "Produto: " + nome + "\n" +
						 "Preço: R$ " + preco + "\n" +
						 "Estoque: " + estoque + " unidades\n" +
						 "Descrição: " + descricao + "\n" +
						 "Disponível: " + (disponivel ? "Sim" : "Não");
						 
		System.out.println(detalhes);
	}
	
	

}
