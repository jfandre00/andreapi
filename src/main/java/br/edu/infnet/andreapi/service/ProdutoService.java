package br.edu.infnet.andreapi.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.andreapi.model.domain.Produto;
import br.edu.infnet.andreapi.model.exceptions.ValorInvalidoException;

// O serviço é criado como objeto, não estático
public class ProdutoService {

    // A lista de produtos agora pertence EXCLUSIVAMENTE ao Service
    private List<Produto> produtos = new ArrayList<>();
    
    // AT: Array de tamanho fixo
    // Vamos guardar apenas os últimos 3 cadastrados como um histórico recente
    private Produto[] ultimosCadastrados = new Produto[3];

    
    public void incluir(Produto produto) {
        produtos.add(produto);
 
        // AT: Atualiza o array fixo
        atualizarUltimosCadastrados(produto);
        
        
        System.out.println("Produto cadastrado com sucesso!");
        
        // AT: Vamos chamar o método da Interface
        produto.imprimirRelatorio();
    }
    
    // AT: Método para gerenciar o Array Fixo
    private void atualizarUltimosCadastrados(Produto novo) {
        // vai descartando o mais antigo [0] para a posição [1]
        ultimosCadastrados[0] = ultimosCadastrados[1];
        ultimosCadastrados[1] = ultimosCadastrados[2];
        
        // Coloca o novo na última posição [2]
        ultimosCadastrados[2] = novo;
    }
    
    // AT: Iteração com array fixo 
    public void listarUltimos() {
        System.out.println("--- Últimos 3 Cadastrados ---");
        
        boolean temProduto = false; // controle

        for (int i = 0; i < ultimosCadastrados.length; i++) {
            // Se a posição não for nula, imprime e marca o controle como true
            if (ultimosCadastrados[i] != null) {
                System.out.println((i + 1) + "º: " + ultimosCadastrados[i].getNome());
                temProduto = true;
            }
        }
        
        // Se rodou o loop todo e o controle false, é porque estava tudo vazio
        if (!temProduto) {
            System.out.println("Nenhum produto cadastrado recentemente.");
        }
    }
    

    public List<Produto> listarTodos() {
        return produtos;
    }

    // Regra de Negócio: Aplica desconto no último item
    public void aplicarDescontoPercentual(double percentual) throws ValorInvalidoException {
    	// chamando o método auxiliar
        Produto ultimoProduto = obterUltimoProduto();
        
        // se chegou aqui, é porque há produtos
        if (ultimoProduto != null) {
            System.out.println("Aplicando desconto em: " + ultimoProduto.getNome());
            ultimoProduto.aplicarDesconto(percentual);
        }
    }
    
    // Método para aplicar desconto fixo (similar ao anterior)
    public void aplicarDescontoFixo(double valor) throws ValorInvalidoException {
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