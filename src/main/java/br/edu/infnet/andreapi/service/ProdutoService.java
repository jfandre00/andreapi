package br.edu.infnet.andreapi.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.andreapi.model.domain.Produto;

// O serviço é criado como objeto, não estático
public class ProdutoService {

    // A lista de produtos agora pertence EXCLUSIVAMENTE ao Service
    private List<Produto> produtos = new ArrayList<>();

    public void incluir(Produto produto) {
        produtos.add(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public List<Produto> listarTodos() {
        return produtos;
    }

    // Regra de Negócio: Aplica desconto no último item
    public void aplicarDescontoPercentual(double percentual) {
    	// chamando o método auxiliar
        Produto ultimoProduto = obterUltimoProduto();
        
        // se chegou aqui, é porque há produtos
        if (ultimoProduto != null) {
            System.out.println("Aplicando desconto em: " + ultimoProduto.getNome());
            ultimoProduto.aplicarDesconto(percentual);
        }
    }
    
    // Método para aplicar desconto fixo (similar ao anterior)
    public void aplicarDescontoFixo(double valor) {
    	// chamando o método auxiliar
        Produto ultimoProduto = obterUltimoProduto();
        
        if (ultimoProduto != null) {
            System.out.println("Aplicando desconto FIXO em: " + ultimoProduto.getNome());
            ultimoProduto.aplicarDesconto(valor, true);
        }
    }
    
    // NOVO MÉTODO PRIVADO AUXILIAR - refatoração pois havia repetição de código nos dois métodos acima
    private Produto obterUltimoProduto() {
        if (produtos.isEmpty()) {
            System.out.println("Erro: Não há produtos para aplicar desconto.");
            return null;
        }
        return produtos.get(produtos.size() - 1);
    }
}