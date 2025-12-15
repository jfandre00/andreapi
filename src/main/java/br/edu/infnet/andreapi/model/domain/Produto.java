package br.edu.infnet.andreapi.model.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import br.edu.infnet.andreapi.interfaces.IPrinter;
import br.edu.infnet.andreapi.model.exceptions.ValorInvalidoException;

// Alteração para AT -> Agora Produto é abstract e utiliza uma Interface
public abstract class Produto implements IPrinter {
	

	// Constante de taxa padrão de serviço que o InfnetFood cobra (10%)
	public static final float TAXA_PADRAO_SERVICO = 0.10f; 
	
	// AT: Modificador default - ele será visível apenas dentro do domain
	// Podemos usar esse atributo para controle interno, somente as classes do domain terão acesso
	String codigoControleInterno;
	
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
        // Inicializa o default
        this.codigoControleInterno = "CONTROLE-PADRAO";
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
    // para o AT: Adicionei o 'throws ValorInvalidoException' para que o método possa lançar a exceção personalizada
	public void aplicarDesconto(double percentual) throws ValorInvalidoException {
	        
        if (percentual <= 0 || percentual >= 100) {
        	
        	// Não usarei mais o System.out.println aqui
        	throw new ValorInvalidoException("O percentual de desconto deve ser entre 1% e 99%.");
        }

        if (!disponivel) {
        	throw new ValorInvalidoException("Não é possível aplicar desconto em produto indisponível.");
        }

        // Obrigado pelo feedback, Prof. Elberth! Agora está mais limpo e nem precisou do else.
        
        BigDecimal novoPreco = calcularPrecoComDesconto(percentual);
        this.preco = novoPreco;
        System.out.println("Desconto de " + percentual + "% aplicado.");
        System.out.println("Novo preço: R$" + this.preco);
	    }
	
	// SOBRECARGA: Mesmo nome, parâmetros diferentes. Feature 03
    // Permite aplicar um desconto fixo em reais, em vez de porcentagem.
	// para o AT: Adicionei o 'throws ValorInvalidoException' para que o método possa lançar a exceção personalizada
    public void aplicarDesconto(double valorDescontoDouble, boolean isValorFixo) throws ValorInvalidoException {
        if (isValorFixo) {
        	
        	// Convertendo o double recebido para BigDecimal para fazer a conta
            BigDecimal valorDesconto = BigDecimal.valueOf(valorDescontoDouble);
            
            if (valorDesconto.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValorInvalidoException("O valor do desconto deve ser positivo.");
           }
           
           if (this.preco.compareTo(valorDesconto) < 0) {
               throw new ValorInvalidoException("O valor do desconto (" + valorDesconto + ") é maior que o preço do produto (" + this.preco + ").");
           } 
           
           // Se passou das validações vai aplicar o desconto
           this.preco = this.preco.subtract(valorDesconto);
           this.preco = this.preco.setScale(2, RoundingMode.HALF_UP);
           System.out.println("Desconto de R$" + valorDescontoDouble + " aplicado.");
           System.out.println("Novo preço: R$" + this.preco);
        } else {
            // Se não for fixo, assume que é porcentagem e chama o outro método
        	// E esse método abaixo já lança a exceção se houver problema
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
	       
	        
	     // Locale.US para garantir que saia 39.90 e não 39,90 e com 2 casas decimais, padronizando a saída
	        return String.format(Locale.US, 
	            "Produto: %-35s | R$ %6.2f | Estoque: %03d un. | Categoria: %-40s", 
	            this.nome, 
	            this.preco, 
	            this.estoque, 
	            categoriaStr
	        );
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
		
		// Implementação obrigatória do método da interface - coloquei na classe mãe para as filhas herdarem, pois o relatório é igual
	    @Override
	    public void imprimirRelatorio() {
	        System.out.println("### RELATÓRIO DO PRODUTO ###");
	        System.out.println(this.toString());
	        System.out.println("Taxa de Serviço Padrão: " + (TAXA_PADRAO_SERVICO * 100) + "%");
	        System.out.println("############################");
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
