package br.edu.infnet.andreapi.model.domain;

public class Categoria {

    private Integer id;
    private String descricao;
    private TipoCategoria tipo; // Relacionamento com Enum

    // Construtor Padrão
    public Categoria() {
    }

    // Construtor com parâmetros
    public Categoria(Integer id, String descricao, TipoCategoria tipo) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    // Encapsulamento
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public TipoCategoria getTipo() { return tipo; }
    public void setTipo(TipoCategoria tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return descricao + " (" + tipo + ")";
    }
}