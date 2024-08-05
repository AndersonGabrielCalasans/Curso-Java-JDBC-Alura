package br.com.gabrielcalasans.loja.modelo;

import jakarta.persistence.Embeddable;

// Recurso usado para separar em Classes diferentes colunas de uma mesma tabela
@Embeddable
public class DadosPessoais {

    private String nome;
    private String cpf;

    public DadosPessoais(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public DadosPessoais() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
