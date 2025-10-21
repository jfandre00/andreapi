// tentando entender e estudando java

package br.edu.infnet.andreapi.model.domain;

public class Teste {

	public String nome;
	public int idade;
	public double altura;
	public double peso;
	public boolean ativo;
	
	private double calcularIMC() {
		return peso / (altura * altura);
	}
	
	public double imprimirInformacoes() {
		System.out.println("Nome: " + nome + " tem idade " + idade + " anos, altura " + altura + " m, peso " + peso + " kg");
		System.out.println("Ativo: " + (ativo? "Sim" : "Não"));
		
		double imc = calcularIMC();
		
		imc = Math.round(imc * 100.0) / 100.0; // arredondar para duas casas decimais
		// multiplicar por 100 e depois dividir por 100 para manter o valor com duas casas decimais
			
		System.out.println("IMC: " + imc);
		
		return imc;
	}
	
	
}
