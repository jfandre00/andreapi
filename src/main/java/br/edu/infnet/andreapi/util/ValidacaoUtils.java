package br.edu.infnet.andreapi.util;

import java.math.BigDecimal;
import java.util.Scanner;

// Vou utilizar métodos estáticos pois poderei chamá-los sem precisar instanciar a classe

public class ValidacaoUtils {

    // Método para ler BigDecimal (usado para Preço e Desconto R$)
    public static BigDecimal lerBigDecimal(Scanner in, String prompt) {
        BigDecimal valorFinal = BigDecimal.valueOf(-1); // Inicializa inválido
        
        while (valorFinal.compareTo(BigDecimal.ZERO) < 0) { // Repete enquanto for negativo
            System.out.print(prompt);
            
            if (!in.hasNextDouble()) {
                String entradaInvalida = in.next();
                System.out.println("Erro: Entrada inválida! [" + entradaInvalida + "] não é um número.");
            } else {
                double valorDigitado = in.nextDouble();
                if (valorDigitado < 0) {
                    System.out.println("O valor não pode ser negativo!");
                } else {
                    // Valor é válido e positivo, converte para BigDecimal
                    valorFinal = BigDecimal.valueOf(valorDigitado);
                }
            }
            in.nextLine(); // Limpa o buffer
        }
        return valorFinal;
    }

    // Método para ler Int (usado para Estoque e Opções)
    public static int lerInt(Scanner in, String prompt) {
        int valorFinal = -1;
        
        while (valorFinal < 0) {
            System.out.print(prompt);
            
            if (!in.hasNextInt()) {
                String entradaInvalida = in.next();
                System.out.println("Erro: Entrada inválida! [" + entradaInvalida + "] não é um número inteiro.");
            } else {
                int valorDigitado = in.nextInt();
                if (valorDigitado < 0) {
                    System.out.println("O valor não pode ser negativo!");
                } else {
                    valorFinal = valorDigitado;
                }
            }
            in.nextLine(); // Limpa o buffer
        }
        return valorFinal;
    }

    // Método específico para ler o percentual de desconto (entre 0 e 100)
    public static double lerPercentual(Scanner in, String prompt) {
        double valorFinal = -1; // Começa inválido
        
        while (valorFinal <= 0 || valorFinal >= 100) {
            System.out.print(prompt);
            
            if (!in.hasNextDouble()) {
                String entradaInvalida = in.next();
                System.out.println("Erro: Entrada inválida! [" + entradaInvalida + "]");
            } else {
                double valorDigitado = in.nextDouble();
                if (valorDigitado <= 0 || valorDigitado >= 100) {
                    System.out.println("O percentual deve ser maior que 0 e menor que 100.");
                } else {
                    valorFinal = valorDigitado;
                }
            }
            in.nextLine();
        }
        return valorFinal;
    }
    
    // método que lê um inteiro mas obriga a estar entre min e max
    public static int lerIntIntervalo(Scanner in, String prompt, int min, int max) {
        int valor;
        while (true) {
            // Reutiliza o lerInt para garantir que é um número inteiro positivo
            valor = lerInt(in, prompt); 
            
            // Verifica se está dentro do intervalo permitido
            if (valor >= min && valor <= max) {
                return valor; // Valor válido, retorna e sai do método
            }
            
            System.out.println("Opção inválida! Escolha um valor entre " + min + " e " + max + ".");
        }
    }
    
    // Novo método criado ->  Lê uma String e não aceita valor vazio ou apenas espaços
    // notei que o usuário conseguiria deixar os campos String vazios, o que não é legal.
    
    public static String lerString(Scanner in, String prompt) {
        String entrada;
        
        while (true) {
            System.out.print(prompt);
            entrada = in.nextLine().trim(); // .trim() p/ remover espaços antes e depois
            
            if (!entrada.isEmpty()) {
                return entrada; // Se não for vazio, pode retornar
            }
            
            System.out.println("Erro: Este campo não pode ficar vazio!");
        }
    }
}