package br.com.gabrielcalasans.loja.dao;

import br.com.gabrielcalasans.loja.modelo.Produto;
import jakarta.persistence.EntityManager;

public class ProdutoDAO {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Produto produto) {
        this.entityManager.persist(produto);
    }
}
