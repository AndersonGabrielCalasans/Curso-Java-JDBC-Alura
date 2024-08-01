package br.com.gabrielcalasans.loja.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "produtos")
public class Produto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String nome;
        String descricao;
        BigDecimal preco;
        @Column(name = "data_criacao")
        private LocalDate dataCadastro;
//        @Enumerated(EnumType.STRING) // Cadastra como String no banco de dados
        @ManyToOne // Cada produto tem uma categoria e uma categoria tem muitos produtos
        private Categoria categoria;

        public Produto(String nome, String descricao, BigDecimal preco, Categoria categoria) {
                this.nome = nome;
                this.descricao = descricao;
                this.preco = preco;
                this.categoria = categoria;
                this.dataCadastro = LocalDate.now();
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getDescricao() {
                return descricao;
        }

        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }

        public BigDecimal getPreco() {
                return preco;
        }

        public void setPreco(BigDecimal preco) {
                this.preco = preco;
        }

        public LocalDate getDataCadastro() {
                return dataCadastro;
        }

        public void setDataCadastro(LocalDate dataCadastro) {
                this.dataCadastro = dataCadastro;
        }

        public Categoria getCategoria() {
                return categoria;
        }

        public void setCategoria(Categoria categoria) {
                this.categoria = categoria;
        }
}
