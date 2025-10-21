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

        System.out.println("--- Cadastro de Novo Produto ---");

        // Instanciação da entidade com os dados coletados
        Produto meuProduto = new Produto();
        
        // Coleta de dados via Scanner
        System.out.print("Digite o nome do produto: ");
        meuProduto.nome = in.nextLine();

        System.out.print("Digite o preço do produto: ");
        meuProduto.preco = in.nextDouble();

        System.out.print("Digite a quantidade em estoque: ");
        meuProduto.estoque = in.nextInt();
        
        // Limpa o buffer do scanner antes de ler a próxima linha
        in.nextLine();
        
        System.out.print("Digite a descrição do produto: ");
        meuProduto.descricao = in.nextLine();

        System.out.print("O produto está disponível? (true/false): ");
        meuProduto.disponivel = in.nextBoolean();
        
        // Limpa o buffer do scanner 
        in.nextLine(); 


        System.out.println("\n--- Produto Cadastrado com Sucesso! ---");
        System.out.println("Informações Iniciais: ");
        meuProduto.imprimirDetalhes();
        System.out.println("----------------------------------------");

        // Invocar o método público
        System.out.print("\nDigite um percentual de desconto para aplicar: ");
        double desconto = in.nextDouble();
        meuProduto.aplicarDesconto(desconto);

        System.out.println("\n--- Informações Finais do Produto ---");
        // Exibir informações finais
        meuProduto.imprimirDetalhes();
        System.out.println("-------------------------------------");

        // Fechar o scanner
        in.close();
	}

}

