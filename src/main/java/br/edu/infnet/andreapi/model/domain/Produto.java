package br.edu.infnet.andreapi.model.domain;

public class Produto {
	
	// Atributos essenciais
	
	public int estoque;
	public String nome;
	public boolean disponivel;
	public double preco;
	public String descricao;
	
	// Métodos Personalizados
	
	
	// Feature 01: Método público que aplica um desconto ao preço do produto. Ele chama um método privado para fazer o cálculo.
	// Para a Feature 02, esse método irá verificar multiplas condições antes de aplicar o desconto.
	public void aplicarDesconto(double percentual) {
		if (percentual > 0 && percentual < 100) {
			
			
			if (percentual <= 0 || percentual >= 100) {
				System.out.println("Percentual de desconto inválido. O preço não foi alterado.");
			
			} else if (!disponivel) {
				System.out.println("Não é possível aplicar desconto em um produto indisponível.");
			

			} else { // percentual válido e produto disponível
				double novoPreco = calcularPrecoComDesconto(percentual);
				preco = novoPreco;
				System.out.println("Desconto de " + percentual + "% aplicado. Novo preço: R$ " + novoPreco);
			}
		}
	}
	
	// Método privado que calcula o valor do produto com o desconto aplicado.
	private double calcularPrecoComDesconto(double percentual) {
		double valorDoDesconto = (preco * percentual) / 100;
		
		// vamos arredondar o valor do desconto para duas casas decimais
			
		return Math.round((preco - valorDoDesconto) * 100.0) / 100.0;
	}
	
	
	// Para a Feature 02: Vamos adicionar um "Status" calculado.
	public void imprimirDetalhes() {
		
		String statusCalculado;
		
		if (disponivel && estoque > 0) {
			statusCalculado = "Disponível em estoque";
			
		} else if (disponivel && estoque == 0) {
			statusCalculado = "Disponível (Fora de estoque)";
		
		} else {
			statusCalculado = "Indisponível (Cadastro inativo)";
		}
		
		String detalhes = "Produto: " + nome + "\n" +
						 "Preço: R$ " + preco + "\n" +
						 "Estoque: " + estoque + " unidades\n" +
						 "Descrição: " + descricao + "\n" +
						 "Disponível: " + (disponivel ? "Sim" : "Não") +
						 "\nStatus: " + statusCalculado;
						 
		System.out.println(detalhes);
	}
	
	/**
	 * Feature 2 - requisito 3
	 * Método que usa um for para simular uma previsão de preço p/ um nº fixo de meses.
	 */
	public void exibirPrevisaoDePreco(int totalMeses) {
		if (this.preco <= 0) {
			System.out.println("Não é possível calcular a previsão para um produto sem preço ou com preço zero.");
			return; // esse return é como um break e sai do método
		}

		System.out.println("--- Previsão de Preço (próximos " + totalMeses + " meses) ---");
		
		double precoAtual = this.preco;
		
		for (int mes = 1; mes <= totalMeses; mes++) {
			
			// Vamos simular que no mês 3 há uma promoção e o preço não muda, para usarmos o continue (ex 4)
			if (mes == 3) {
				System.out.println("Mês " + mes + ": Promoção! O preço se mantém: R$ " + (Math.round(precoAtual * 100.0) / 100.0));
				continue; // irá pular o resto do loop e move p/ a prox iteração (mês 4)
			}
			
			// Simulação de aumento de 2% ao mês - depois vou criar uma constante para isso
			precoAtual = precoAtual * 1.02; 

			double precoArredondado = Math.round(precoAtual * 100.0) / 100.0;
			
			System.out.println("Mês " + mes + ": R$ " + precoArredondado);
		}
	}
	
	

}
