package br.com.gabrielcalasans.loja.dao;

import br.com.gabrielcalasans.loja.modelo.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class CategoriaDAO {

    private EntityManager entityManager;

    public CategoriaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Categoria categoria) {
        Categoria categoriaBuscada = this.buscarPorNome(categoria.getNome());
        if (categoriaBuscada == null) {
            this.entityManager.persist(categoria);
            System.out.println("Categoria cadastrada com sucesso!");
        } else {
            this.atualizar(categoria);
            System.out.println("Categoria já consta no sistema.");
        }
    }

    public Categoria atualizar(Categoria categoria) {
        return this.entityManager.merge(categoria);
    }

    public void remover(Categoria categoria) {
        categoria = entityManager.merge(categoria);
        entityManager.remove(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return entityManager.find(Categoria.class, id);
    }

    public Categoria buscarPorNome(String nome) {
        String jpql = "SELECT c FROM Categoria c WHERE c.nome = :nome";
        try {
            return entityManager.createQuery(jpql, Categoria.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
