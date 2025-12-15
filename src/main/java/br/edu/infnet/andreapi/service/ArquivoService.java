package br.edu.infnet.andreapi.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.andreapi.model.domain.Bebida;
import br.edu.infnet.andreapi.model.domain.Categoria;
import br.edu.infnet.andreapi.model.domain.Comida;
import br.edu.infnet.andreapi.model.domain.Produto;
import br.edu.infnet.andreapi.model.domain.TipoCategoria;

public class ArquivoService {

    private final String NOME_ARQUIVO = "produtos.txt"; 

 
    public void gravarArquivo(List<Produto> produtos) {
        // no try and catch com recursos, o BufferedWriter será fechado automaticamente
        // false dentro do FileWriter pois queremos sobrescrever o arquivo a cada gravação
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, false))) { 

            for (Produto p : produtos) {
                // padrão utilizado -> TIPO;NOME;PRECO;ESTOQUE;CATEGORIA;DADOS_ESPECIFICOS
                StringBuilder linha = new StringBuilder();
                
                // Prefixo para saber qual classe instanciar na leitura
                if (p instanceof Comida) {
                    linha.append("C;");
                    Comida c = (Comida) p;
                    linha.append(c.getPesoKg()).append(";").append(c.isOrganico());
                } else if (p instanceof Bebida) {
                    linha.append("B;");
                    Bebida b = (Bebida) p;
                    linha.append(b.getTamanhoMl()).append(";").append(b.isAlcoolica());
                }

                // Adiciona dados comuns no final
                linha.append(";").append(p.getNome())
                     .append(";").append(p.getPreco())
                     .append(";").append(p.getEstoque())
                     .append(";").append(p.getCategoria().getDescricao()) // descrição da categoria
                     .append(";").append(p.getCategoria().getTipo());     // utilizando o Enum

                writer.write(linha.toString());
                writer.newLine(); // Pula linha
            }
            
            System.out.println("Dados gravados com sucesso no arquivo: " + NOME_ARQUIVO);

        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }

    public List<Produto> lerArquivo() {
        List<Produto> listaLida = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                
                // padrao -> [0]Tipo [1]DadoEspec1 [2]DadoEspec2 [3]Nome [4]Preco [5]Estoque [6]DescCat [7]EnumCat
                
                String nome = campos[3];
                BigDecimal preco = new BigDecimal(campos[4]);
                int estoque = Integer.parseInt(campos[5]);
                
                // Reconstrói a Categoria
                String descCat = campos[6];
                TipoCategoria tipoCat = TipoCategoria.valueOf(campos[7]);
                Categoria categoria = new Categoria(null, descCat, tipoCat);
                
                Produto produto = null;

                // Irei utilizar o tratamento que o Prof. Elberth fez na aula de 10-12-2025
                // if(campos[0].equals("C")) {...} iria gerar NullPointerException se fosse null
                // então usamos "C".equals(campos[0]) para evitar isso, pois "C" ou "B" nunca será null
                if ("C".equalsIgnoreCase(campos[0])) { 
                    float peso = Float.parseFloat(campos[1]);
                    boolean organico = Boolean.parseBoolean(campos[2]);
                    produto = new Comida(nome, preco, estoque, categoria, peso, organico);
                    
                } else if ("B".equalsIgnoreCase(campos[0])) {
                    int ml = Integer.parseInt(campos[1]);
                    boolean alcoolica = Boolean.parseBoolean(campos[2]);
                    produto = new Bebida(nome, preco, estoque, categoria, ml, alcoolica);
                }

                if (produto != null) {
                    produto.setDisponivel(true); // está disponível por padrão ao ler
                    listaLida.add(produto);
                }
            }
            
            System.out.println("Arquivo lido com sucesso! " + listaLida.size() + " produtos carregados.");

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados não encontrado. Iniciando lista vazia.");
        } catch (IOException e) {
            System.err.println("Erro de leitura: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao processar dados do arquivo: " + e.getMessage());
        }

        return listaLida;
    }
}