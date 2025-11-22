package br.edu.infnet.andreapi.model.domain;

public class Produto {
	

	// Tornei meus atributos privados
	private int estoque;
	private String nome;
	private boolean disponivel;
	private double preco;
	private String descricao;
	
	private Categoria categoria; // Relacionamento com a classe Categoria
	
	// Construtor padrão
	public Produto() {
	}
	
	// Construtor com Nome e Preço (Estado inicial específico)
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = true; // Define um padrão
    }

    // Construtor Completo (Chama o construtor acima para evitar duplicação)
    public Produto(String nome, double preco, int estoque, Categoria categoria) {
        this(nome, preco); // Chama o construtor de 2 parâmetros (Reaproveitamento)
        this.estoque = estoque;
        this.categoria = categoria;
    }
	
	
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
	
	// SOBRECARGA: Mesmo nome, parâmetros diferentes. Feature 03
    // Permite aplicar um desconto fixo em reais, em vez de porcentagem.
    public void aplicarDesconto(double valorDesconto, boolean isValorFixo) {
        if (isValorFixo) {
            if (valorDesconto < this.preco) {
                this.preco -= valorDesconto;
                System.out.println("Desconto de R$" + valorDesconto + " aplicado.");
            } else {
                System.out.println("Erro: Desconto maior que o preço.");
            }
        } else {
            // Se não for fixo, assume que é porcentagem e chama o outro método
            aplicarDesconto(valorDesconto);
        }
    }
	
	// Método privado que calcula o valor do produto com o desconto aplicado.
	private double calcularPrecoComDesconto(double percentual) {
		double valorDoDesconto = (preco * percentual) / 100;
		
		// vamos arredondar o valor do desconto para duas casas decimais
			
		return Math.round((preco - valorDoDesconto) * 100.0) / 100.0;
	}
	
	// Método toString() para representação textual
	@Override
    public String toString() {
		// Verifica se a categoria é nula para evitar problemas
        String categoriaStr = (categoria != null) ? categoria.toString() : "Sem categoria";
        
        return "Produto: " + nome + " | R$ " + preco + 
               " | Estoque: " + estoque + 
               " | Categoria: " + categoriaStr;
    }
	
	
	// Para a Feature 02: Vamos adicionar um "Status" calculado.
    // Feature 03: Mantivemos o imprimirDetalhes antigo para compatibilidade, mas usando toString é melhor
	public void imprimirDetalhes() {
		// Código antigo comentado para referência
		/*
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
		*/
		
		System.out.println(this.toString());
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
	
	//Métodos de Encapsulamento
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
	
	

}
