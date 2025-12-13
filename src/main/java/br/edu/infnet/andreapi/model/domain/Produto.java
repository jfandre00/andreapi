package br.edu.infnet.andreapi.model.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Alteração para AT -> Agora Produto é abstract
public abstract class Produto {
	

	// Atributos eram privados, para o AT viraram protected para as filhas (Comida e Bebida) acessarem
	
	protected String nome;
	protected BigDecimal preco;
	protected int estoque;
	protected boolean disponivel;
	protected String descricao;
	protected Categoria categoria; // Relacionamento com a classe Categoria
	
	// Construtor padrão
	public Produto() {
	}
	
	// Construtor com Nome e Preço (Estado inicial específico)
    public Produto(String nome, BigDecimal preco) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = true; // Define um padrão
    }

    // Construtor Completo 
    public Produto(String nome, BigDecimal preco, int estoque, Categoria categoria) {
        this(nome, preco); // Feature 3/Feedback do Prof. Elberth: Construtor chamando outro para evitar duplicação
        this.estoque = estoque;
        this.categoria = categoria;
    }
	
	
	// Feature 01: Método público que aplica um desconto ao preço do produto. Ele chama um método privado para fazer o cálculo.
	// Para a Feature 02, esse método irá verificar multiplas condições antes de aplicar o desconto.
    // Refatorei o método após o feedback do Prof. Elberth pois havia uma verificação redundante. (25-11-2025)
	public void aplicarDesconto(double percentual) {
	        
        if (percentual <= 0 || percentual >= 100) {
            System.out.println("Erro: Percentual inválido.");
            return; // Verificação feita somente uma vez
        }

        if (!disponivel) {
            System.out.println("Erro: Produto indisponível.");
            return; 
        }

        // Obrigado pelo feedback, Prof. Elberth! Agora está mais limpo e nem precisou do else.
        
        BigDecimal novoPreco = calcularPrecoComDesconto(percentual);
        this.preco = novoPreco;
        System.out.println("Desconto de " + percentual + "% aplicado.");
        System.out.println("Novo preço: R$" + this.preco);
	    }
	
	// SOBRECARGA: Mesmo nome, parâmetros diferentes. Feature 03
    // Permite aplicar um desconto fixo em reais, em vez de porcentagem.
    public void aplicarDesconto(double valorDescontoDouble, boolean isValorFixo) {
        if (isValorFixo) {
        	
        	// Convertendo o double recebido para BigDecimal para fazer a conta
            BigDecimal valorDesconto = BigDecimal.valueOf(valorDescontoDouble);
            
            // Comparações em BigDecimal usam .compareTo()
            // se preco < valorDesconto
            if (this.preco.compareTo(valorDesconto) < 0) {
                System.out.println("Erro: Desconto maior que o preço.");
            } else {
                // Subtração: this.preco = this.preco.subtract(valorDesconto)
                this.preco = this.preco.subtract(valorDesconto);
                // Arredonda para 2 casas
                this.preco = this.preco.setScale(2, RoundingMode.HALF_UP);
                System.out.println("Desconto de R$" + valorDescontoDouble + " aplicado.");
                System.out.println("Novo preço: R$" + this.preco);
            }
        } else {
            // Se não for fixo, assume que é porcentagem e chama o outro método
            aplicarDesconto(valorDescontoDouble);
        }
    }
	
	// Método privado que calcula o valor do produto com o desconto aplicado.
	private BigDecimal calcularPrecoComDesconto(double percentual) {
		// Lógica: Preço - (Preço * (Percentual / 100))
        
        BigDecimal percentualBD = BigDecimal.valueOf(percentual)
                .divide(BigDecimal.valueOf(100)); // Divide por 100
        
        BigDecimal valorDoDesconto = this.preco.multiply(percentualBD); // Multiplica
        
        BigDecimal resultado = this.preco.subtract(valorDoDesconto); // Subtrai
        
        // 2 casas decimais com arredondamento padrão bancário
        return resultado.setScale(2, RoundingMode.HALF_UP);
	}
	
	
	
	/**
	 * Feature 2 - requisito 3
	 * Método que usa um for para simular uma previsão de preço p/ um nº fixo de meses.
	 */
	public void exibirPrevisaoDePreco(int totalMeses) {
        if (this.preco.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Não é possível calcular a previsão para um produto sem preço.");
            return;
        }

        System.out.println("--- Previsão de Preço (próximos " + totalMeses + " meses) ---");
        
        BigDecimal precoAtual = this.preco;
        // Fator de aumento de 2% ( x 1.02)
        BigDecimal fatorAumento = BigDecimal.valueOf(1.02);
        
        for (int mes = 1; mes <= totalMeses; mes++) {
            if (mes == 3) {
                System.out.println("Mês " + mes + ": Promoção! O preço se mantém: R$ " + precoAtual);
                continue;
            }
            
            // Multiplica e arredonda
            precoAtual = precoAtual.multiply(fatorAumento).setScale(2, RoundingMode.HALF_UP);
            
            System.out.println("Mês " + mes + ": R$ " + precoAtual);
        }
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
		
		
	
	//Métodos de Encapsulamento
    
	public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // MUDANÇA: Getter e Setter devem ser BigDecimal
    public BigDecimal getPreco() { return preco; }
    
    public void setPreco(BigDecimal preco) { 
        this.preco = preco; 
    }
    // Sobrecarga utilitária para setar com double (caso necessário)
    public void setPreco(double preco) {
        this.preco = BigDecimal.valueOf(preco);
    }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
	
	

}
