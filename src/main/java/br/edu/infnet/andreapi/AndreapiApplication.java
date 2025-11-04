// Backend do Infnet Food - Andre API

package br.edu.infnet.andreapi;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.andreapi.model.domain.Produto;

@SpringBootApplication
public class AndreapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndreapiApplication.class, args);
        
        Scanner in = new Scanner(System.in);
        
        // Irei guardar o produto aqui para que ele exista fora do loop. No futuro poderei criar uma lista de produtos.
        Produto produtoCadastrado = null; 
        
        int opcao = -1;

        // Loop principal do menu
        do {
            System.out.println("\n--- Menu Principal InfnetFood ---");
            System.out.println("1. Cadastrar Novo Produto");
            System.out.println("2. Ver Detalhes do Produto");
            System.out.println("3. Aplicar Desconto no Produto");
            System.out.println("4. Ver Previsão de Preço");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = in.nextInt();
            in.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.println("--- Cadastro de Novo Produto ---");

                    Produto meuProduto = new Produto();

                    System.out.print("Digite o nome do produto: ");
                    meuProduto.nome = in.nextLine();

                    System.out.print("Digite o preço do produto: ");
                    meuProduto.preco = in.nextDouble();

                    System.out.print("Digite a quantidade em estoque: ");
                    meuProduto.estoque = in.nextInt();
                    in.nextLine(); // Limpa o buffer do scanner
                    
                    System.out.print("Digite a descrição do produto: ");
                    meuProduto.descricao = in.nextLine();

                    System.out.print("O produto está disponível? (true/false): ");
                    meuProduto.disponivel = in.nextBoolean();  
                    in.nextLine(); // Limpa o buffer do scanner 

                    System.out.println("\n--- Produto Cadastrado com Sucesso! ---");
                    meuProduto.imprimirDetalhes();
                    System.out.println("----------------------------------------");
                    
                    // Salva o produto na variável que criei acima
                    produtoCadastrado = meuProduto; 
                    break;
                    
                case 2:
                    System.out.println("--- Detalhes do Produto ---");
                    if (produtoCadastrado != null) {
                        produtoCadastrado.imprimirDetalhes();
                    } else {
                        System.out.println("Nenhum produto foi cadastrado ainda. Use a opção 1.");
                    }
                    System.out.println("---------------------------");
                    break;
                    
                case 3:
                	if (produtoCadastrado != null) {
	                    double desconto = -1; // para forçar a entrada no loop
	
	                    while (desconto <= 0 || desconto >= 100) {
	                        System.out.print("\nDigite um percentual de desconto VÁLIDO (Ex: 10 para 10%): ");
	                        desconto = in.nextDouble();
	                        in.nextLine(); // Limpa o buffer
	                        
	                        if (desconto <= 0 || desconto >= 100) {
	                            System.out.println("Valor inválido! O desconto deve ser maior que 0 e menor que 100.");
	                        }
	                    }
                        
                        // Se saiu do loop é pq o desconto é válido
                        produtoCadastrado.aplicarDesconto(desconto);
                        System.out.println("\n--- Informações Finais do Produto ---");
                        produtoCadastrado.imprimirDetalhes();
                        System.out.println("-------------------------------------");
                    } else {
                        System.out.println("Nenhum produto foi cadastrado ainda. Use a opção 1.");
                    }
                    break;
                    
                case 4:
                    if (produtoCadastrado != null) {
                        System.out.print("\nDigite o número de meses para a previsão ");
                        int meses = in.nextInt();
                        in.nextLine(); // Limpa buffer
                        
                        if(meses > 0) {
                            produtoCadastrado.exibirPrevisaoDePreco(meses);
                        } else {
                            System.out.println("Número de meses deve ser positivo.");
                        }
                    } else {
                        System.out.println("Nenhum produto foi cadastrado ainda. Use a opção 1.");
                    }
                    break;
                    
                    
                    
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                    
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            if (opcao != 0) {
                System.out.println("\n(Pressione Enter para voltar ao menu)");
                in.nextLine(); // Pausa para o usuário ler a saída
            }

        } while (opcao != 0); // O loop continua enquanto a opção for diferente de 0

        
        in.close(); // Fechar o scanner
        System.out.println("Programa finalizado.");
    }
}
