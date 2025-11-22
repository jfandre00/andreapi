// Backend do Infnet Food - Andre API

package br.edu.infnet.andreapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.infnet.andreapi.model.domain.Categoria;
import br.edu.infnet.andreapi.model.domain.Produto;
import br.edu.infnet.andreapi.model.domain.TipoCategoria;

@SpringBootApplication
public class AndreapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndreapiApplication.class, args);
        
        Scanner in = new Scanner(System.in);
        
        // Usando Lista conforme o requisito da Feature 03
        List<Produto> produtos = new ArrayList<>();
        
        int opcao = -1;

        do {
            System.out.println("\n--- Menu Principal InfnetFood ---");
            System.out.println("1. Cadastrar Novo Produto");
            System.out.println("2. Listar Todos os Produtos");
            System.out.println("3. Aplicar Desconto no Último Produto (Percentual)");
            System.out.println("4. Aplicar Desconto no Último Produto (Valor Fixo)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            // Validação simples para o menu
            if(in.hasNextInt()) {
                opcao = in.nextInt();
            } else {
                in.next(); // Limpa a entrada inválida
                opcao = -1; // Força o default
            }
            in.nextLine(); // Limpa buffer

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Cadastro de Produto ---");
                    
                    System.out.print("Nome: ");
                    String nome = in.nextLine();
                    
                    // VALIDAÇÃO DE PREÇO
                    double preco = -1; // Começa inválido para entrar no loop
                    while (preco < 0) {
                        System.out.print("Preço: ");
                        
                        if (!in.hasNextDouble()) { // Verifica se é número
                            String entradaInvalida = in.next();
                            System.out.println("O preço informado é inválido!! [" + entradaInvalida + "]");
                        } else {
                            double valorDigitado = in.nextDouble();
                            if (valorDigitado < 0) {
                                System.out.println("O preço não pode ser menor que zero!!");
                            } else {
                                preco = valorDigitado; // Valor válido, sai do loop
                            }
                        }
                        in.nextLine(); // Limpa o buffer após ler (ou tentar ler) o número
                    }
                    
                    // --- VALIDAÇÃO DE ESTOQUE ---
                    int estoque = -1;
                    while (estoque < 0) {
                        System.out.print("Estoque: ");
                        
                        if (!in.hasNextInt()) {
                            String entradaInvalida = in.next();
                            System.out.println("O estoque informado é inválido!! [" + entradaInvalida + "]");
                        } else {
                            int valorDigitado = in.nextInt();
                            if (valorDigitado < 0) {
                                System.out.println("O estoque não pode ser negativo!!");
                            } else {
                                estoque = valorDigitado;
                            }
                        }
                        in.nextLine(); // Limpa buffer
                    }

                    System.out.print("Descrição: ");
                    String descricao = in.nextLine();

                    System.out.println("--- Dados da Categoria ---");
                    System.out.print("Descrição da Categoria (ex: Lanches): ");
                    String descCat = in.nextLine();
                    
                    // Validação para o tipo da categoria
                    int tipoOpcao = 0;
                    while (tipoOpcao < 1 || tipoOpcao > 3) {
                        System.out.println("Tipo da Categoria: [1] Comida [2] Bebida [3] Sobremesa");
                        if(in.hasNextInt()) {
                            tipoOpcao = in.nextInt();
                        } else {
                            in.next();
                        }
                        in.nextLine();
                    }
                    
                    TipoCategoria tipoSelecionado = TipoCategoria.OUTROS;
                    if(tipoOpcao == 1) tipoSelecionado = TipoCategoria.COMIDA;
                    else if(tipoOpcao == 2) tipoSelecionado = TipoCategoria.BEBIDA;
                    else if(tipoOpcao == 3) tipoSelecionado = TipoCategoria.SOBREMESA;

                    Categoria novaCategoria = new Categoria(null, descCat, tipoSelecionado);
                    Produto novoProduto = new Produto(nome, preco, estoque, novaCategoria);
                    
                    novoProduto.setDescricao(descricao); 
                    novoProduto.setDisponivel(true);

                    produtos.add(novoProduto);
                    
                    System.out.println("Produto cadastrado com sucesso!");
                    break;
                    
                case 2:
                    System.out.println("\n--- Lista de Produtos ---");
                    if (produtos.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Produto p : produtos) {
                            System.out.println(p);
                            System.out.println("-------------------------");
                        }
                    }
                    break;
                    
                case 3:
                    if (produtos.isEmpty()) {
                        System.out.println("Cadastre um produto primeiro.");
                    } else {
                        Produto ultimoProduto = produtos.get(produtos.size() - 1);
                        System.out.println("Aplicando desconto em: " + ultimoProduto.getNome()); 

                        // VALIDAÇÃO DE DESCONTO (PERCENTUAL)
                        double porc = -1;
                        while (porc <= 0 || porc >= 100) {
                            System.out.print("Digite a % de desconto (entre 1 e 99): ");
                            
                            if(!in.hasNextDouble()) {
                                String invalido = in.next();
                                System.out.println("Valor inválido!! [" + invalido + "]");
                            } else {
                                double valor = in.nextDouble();
                                if (valor <= 0 || valor >= 100) {
                                    System.out.println("A porcentagem deve ser maior que 0 e menor que 100.");
                                } else {
                                    porc = valor;
                                }
                            }
                            in.nextLine();
                        }
                        
                        ultimoProduto.aplicarDesconto(porc);
                        System.out.println("Novo preço: " + ultimoProduto.getPreco()); 
                    }
                    break;
                
                case 4:
                    if (produtos.isEmpty()) {
                        System.out.println("Cadastre um produto primeiro.");
                    } else {
                        Produto ultimoProduto = produtos.get(produtos.size() - 1);
                        System.out.println("Aplicando desconto FIXO em: " + ultimoProduto.getNome());

                        // VALIDAÇÃO DE DESCONTO (VALOR FIXO)
                        double valor = -1;
                        while (valor <= 0) {
                            System.out.print("Digite o valor em R$ para descontar: ");
                            
                            if(!in.hasNextDouble()) {
                                String invalido = in.next();
                                System.out.println("Valor inválido!! [" + invalido + "]");
                            } else {
                                double v = in.nextDouble();
                                if (v <= 0) {
                                    System.out.println("O desconto deve ser positivo.");
                                } else if (v >= ultimoProduto.getPreco()) {
                                    System.out.println("O desconto não pode ser maior que o preço do produto!");
                                } else {
                                    valor = v;
                                }
                            }
                            in.nextLine();
                        }
                        
                        ultimoProduto.aplicarDesconto(valor, true); 
                        System.out.println("Novo preço: " + ultimoProduto.getPreco());
                    }
                    break;
                    
                case 0:
                    System.out.println("Encerrando...");
                    break;
                    
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            if (opcao != 0) {
                System.out.println("\n(Enter para continuar)");
                in.nextLine();
            }

        } while (opcao != 0);

        in.close();
    }
}