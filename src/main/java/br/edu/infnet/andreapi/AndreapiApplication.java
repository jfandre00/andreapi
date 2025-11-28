// Backend do Infnet Food - Andre API

package br.edu.infnet.andreapi;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.andreapi.model.domain.Categoria;
import br.edu.infnet.andreapi.model.domain.Produto;
import br.edu.infnet.andreapi.model.domain.TipoCategoria;
// Criação das classes de validação e servico após feedback do Rayslan (Feature 03) em 28-11-2025.
import br.edu.infnet.andreapi.service.ProdutoService; // Camada de serviço
import br.edu.infnet.andreapi.util.ValidacaoUtils;  // Utilitários de validação

@SpringBootApplication
public class AndreapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndreapiApplication.class, args);
        
        Scanner in = new Scanner(System.in);
        // Instancia o serviço que irá gerenciar os produtos
        ProdutoService produtoService = new ProdutoService(); 
        
        int opcao = -1;

        do {
            System.out.println("\n--- Menu Principal InfnetFood ---");
            System.out.println("1. Cadastrar Novo Produto");
            System.out.println("2. Listar Todos os Produtos");
            System.out.println("3. Aplicar Desconto no Último Produto (Percentual)");
            System.out.println("4. Aplicar Desconto no Último Produto (Valor Fixo)");
            System.out.println("0. Sair");
            
            // Usando a validação para a opção do menu
            // Fiz uma modificação para usar o ValidacaoUtils, que garante a entrada correta. Por conta disso, o default do switch nunca será alcançado.
            // Por isso removi do código. (28-11-2025)
            
            opcao = ValidacaoUtils.lerIntIntervalo(in, "Escolha uma opção: ", 0, 4);

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Cadastro de Produto ---");
                    
                    // mudança para acabar com o problema de entrada de dados vazia
                    // através do lerString (Removi o System.out.print manual daqui)
                    // com isso podemos remover o in.nextLine() referente aos problemas de buffer com o Scanner
                      
                    // também tenho um "problema" com preco, estoque e tipoOpcao, pois se o usuário digitar enter sem nada, o sistema pula uma linha.
                    // Futuramente eu vou mudar para ler uma String e converter para int - OU OUTRA SOLUÇÃO QUE APRENDER EM BREVE
                    // em relação a quebras, o programa está seguro. (28-11-2025)
                    
                    String nome = ValidacaoUtils.lerString(in, "Nome: ");
                    
                    // Chamando o utilitário para ler o preço
                    BigDecimal preco = ValidacaoUtils.lerBigDecimal(in, "Preço: ");
                    
                    // Chamando o utilitário para ler o estoque
                    int estoque = ValidacaoUtils.lerInt(in, "Estoque: ");

                    // mesma mudança para descrição
                    String descricao = ValidacaoUtils.lerString(in, "Descrição: ");

                    System.out.println("--- Dados da Categoria ---");
                    // mesma mudança para descrição da categoria
                    String descCat = ValidacaoUtils.lerString(in, "Descrição da Categoria (ex: Lanches): ");
                    
                    // Chamando o utilitário para ler a opção (1, 2 ou 3)
                    int tipoOpcao = ValidacaoUtils.lerIntIntervalo(in, "Tipo da Categoria: [1] Comida [2] Bebida [3] Sobremesa: ", 1, 3);
                    
                    TipoCategoria tipoSelecionado = TipoCategoria.OUTROS;
                    if(tipoOpcao == 1) tipoSelecionado = TipoCategoria.COMIDA;
                    else if(tipoOpcao == 2) tipoSelecionado = TipoCategoria.BEBIDA;
                    else if(tipoOpcao == 3) tipoSelecionado = TipoCategoria.SOBREMESA;

                    Categoria novaCategoria = new Categoria(null, descCat, tipoSelecionado);
                    Produto novoProduto = new Produto(nome, preco, estoque, novaCategoria);
                    
                    novoProduto.setDescricao(descricao); 
                    novoProduto.setDisponivel(true);

                    // O Serviço é quem lida com a lista
                    produtoService.incluir(novoProduto);
                    break;
                    
                case 2:
                    System.out.println("\n--- Lista de Produtos ---");
                    // O Serviço retorna a lista
                    if (produtoService.listarTodos().isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Produto p : produtoService.listarTodos()) {
                            System.out.println(p);
                            System.out.println("-------------------------");
                        }
                    }
                    break;
                    
                case 3:
                    // Chamando o utilitário para ler o percentual
                    double porc = ValidacaoUtils.lerPercentual(in, "Digite a % de desconto (entre 1 e 99): ");
                    // O Serviço aplica a regra
                    produtoService.aplicarDescontoPercentual(porc);
                    break;
                
                case 4:
                    // Leitura e validação de desconto fixo (R$)
                    BigDecimal valorBD = ValidacaoUtils.lerBigDecimal(in, "Digite o valor em R$ para descontar: ");
                    
                    // A validação de <<desconto maior que preço>> ainda deve ser feita no Service
                    // Por simplicidade, passamos o valor e a lógica de verificação
                    // é tratada no ProdutoService e Produto.
                    produtoService.aplicarDescontoFixo(valorBD.doubleValue());
                    break;
                    
                case 0:
                    System.out.println("Encerrando...");
                    break;
                    
                /*default:
                    System.out.println("Opção inválida.");
                    break;*/
            }

            if (opcao != 0) {
                System.out.println("\n(Enter para continuar)");
                in.nextLine();
            }

        } while (opcao != 0);

        in.close();
    }
}