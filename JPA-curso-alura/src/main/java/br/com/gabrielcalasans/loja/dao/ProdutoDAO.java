package br.com.gabrielcalasans.loja.dao;

import br.com.gabrielcalasans.loja.modelo.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ProdutoDAO {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Produto produto) {
        Produto produtoBuscado = this.buscarPorNome(produto.getNome());
        if (produtoBuscado == null) {
            this.entityManager.persist(produto);
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            this.atualizar(produto);
            System.out.println("Produto já consta no sistema e foi atualizado");
        }
    }

    public Produto atualizar(Produto produto) {
        return this.entityManager.merge(produto);
    }

    public void remover(Produto produto) {
        produto = entityManager.merge(produto);
        entityManager.remove(produto);
    }

    public Produto buscarPorId(Long id) {
        return entityManager.find(Produto.class, id);
    }

    public Produto buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
        try {
            return entityManager.createQuery(jpql, Produto.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Produto> buscaTodos() {
        String jpql = "SELECT p FROM Produto p";
        return entityManager.createQuery(jpql, Produto.class).getResultList();
    }

}
