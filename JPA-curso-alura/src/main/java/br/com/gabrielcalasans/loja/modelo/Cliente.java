package br.com.gabrielcalasans.loja.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

// Recurso usado para separar em Classes diferentes colunas de uma mesma tabela
    @Embedded
    private DadosPessoais dadosPessoais;


    public Cliente(String nome, String cpf) {
        this.setDadosPessoais(new DadosPessoais(nome, cpf));
    }

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }
}
