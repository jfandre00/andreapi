package br.edu.infnet.andreapi.model.exceptions;

// AT: Exceção personalizada (Checked Exception)
public class ValorInvalidoException extends Exception {

	// Gerei um serialVersionUID para evitar warnings (solicitado pelo Eclipse)
	private static final long serialVersionUID = 1L;

	// Construtor que irá receber a mensagem de erro
    public ValorInvalidoException(String mensagem) {
        super(mensagem); // Passa a mensagem para a classe mãe (Exception)
    }
}