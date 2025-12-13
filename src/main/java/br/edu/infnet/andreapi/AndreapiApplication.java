// Backend do Infnet Food - Andre API

package br.edu.infnet.andreapi;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.andreapi.model.domain.Bebida;
import br.edu.infnet.andreapi.model.domain.Categoria;
import br.edu.infnet.andreapi.model.domain.Comida;
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
                    
                    // Implementações do AT -------------------
                    
                    int tipoProduto = ValidacaoUtils.lerIntIntervalo(in, "O que deseja cadastrar? [1] Comida [2] Bebida: ", 1, 2);
                    
                    // Dados que todos têm em comum
                    String nome = ValidacaoUtils.lerString(in, "Nome: ");
                    
                    // Chamando o utilitário para ler o preço
                    BigDecimal preco = ValidacaoUtils.lerBigDecimal(in, "Preço: ");
                    
                    // Chamando o utilitário para ler o estoque
                    int estoque = ValidacaoUtils.lerInt(in, "Estoque: ");

                    // mesma mudança para descrição
                    String descricao = ValidacaoUtils.lerString(in, "Descrição: ");

                    System.out.println("--- Dados da Categoria ---");
                    // mesma mudança para descrição da categoria
                    String descCat = ValidacaoUtils.lerString(in, "Descrição da Categoria (ex: Lanches/Sucos): ");
                    
                   // Resolvendo o problema de ter criado classes novas com o Enum TipoCategoria
                    TipoCategoria tipoSelecionado = TipoCategoria.OUTROS;
                    
                    if (tipoProduto == 2) {
                        // Se já escolheu Bebida lá em cima está pronto, não precisa perguntar de novo
                        tipoSelecionado = TipoCategoria.BEBIDA;
                        System.out.println("Categoria definida automaticamente como: BEBIDA");
                        
                    } else {
                        // Se escolheu Comida, precisamos saber se é Sobremesa ou Comida
                        boolean isSobremesa = ValidacaoUtils.lerBoolean(in, "Este item é uma Sobremesa?");
                        
                        if (isSobremesa) {
                            tipoSelecionado = TipoCategoria.SOBREMESA;
                        } else {
                            tipoSelecionado = TipoCategoria.COMIDA;
                        }
                    }

                    Categoria novaCategoria = new Categoria(null, descCat, tipoSelecionado);
                    Produto novoItem = null; // Utilizando a mãe Produto - Polimorfismo
                    
                    
                    if (tipoProduto == 1) {
                        // É Comida -> Pede peso e se é orgânico
                        System.out.println("--- Detalhes da Comida ---");
                        float peso = ValidacaoUtils.lerFloat(in, "Peso (kg): ");
                        boolean organico = ValidacaoUtils.lerBoolean(in, "É orgânico?");
                        
                        // Instancia Comida
                        novoItem = new Comida(nome, preco, estoque, novaCategoria, peso, organico);
                        
                    } else { // Posso usar else aqui porque a validação já garante que só pode ser 1 ou 2 
                        // É Bebida -> Pede ml e se é alcoólica
                        System.out.println("--- Detalhes da Bebida ---");
                        int ml = ValidacaoUtils.lerInt(in, "Tamanho (ml): ");
                        boolean alcoolica = ValidacaoUtils.lerBoolean(in, "É alcoólica?");
                        
                        // Instancia Bebida
                        novoItem = new Bebida(nome, preco, estoque, novaCategoria, ml, alcoolica);
                    }
                    
                    
                    // Configura dados comuns restantes
                    novoItem.setDescricao(descricao); 
                    novoItem.setDisponivel(true);

                    // O Serviço é quem lida com a lista
                    produtoService.incluir(novoItem);
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
                 
                // Removido após implementação do ValidacaoUtils pois virou redundante   
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