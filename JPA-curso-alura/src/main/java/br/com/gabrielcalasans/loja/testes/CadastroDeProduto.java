package br.com.gabrielcalasans.loja.testes;

import br.com.gabrielcalasans.loja.dao.ProdutoDAO;
import br.com.gabrielcalasans.loja.modelo.Categoria;
import br.com.gabrielcalasans.loja.modelo.Produto;
import br.com.gabrielcalasans.loja.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class CadastroDeProduto {

    public static void main(String[] args) {

        // Cria um objeto da class Produto
        // id é gerado automaticamente pelo banco de dados
        Produto produto = new Produto("Iphone 15", "Celular da marca Apple", new BigDecimal("99.99"), Categoria.CELULARES);

        // Cria o EntityManager para persistencia do objeto no banco de dados
        // A criação do Factory foi jogada para uma classe JPAUtil
        EntityManager entityManager = JPAUtil.getEntityManager();

        // Cria uma classe ProdutoDAO
        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);

        // Cria a requisição para transação (pois: transaction-type="RESOURCE_LOCAL")
        // Com o JTA não precisa desse comando
        entityManager.getTransaction().begin();

        // Faz a inserção no banco
        produtoDAO.cadastrar(produto);

        // Faz o commit da transação
        entityManager.getTransaction().commit();

        // Fecha a transação
        entityManager.close();

    }
}
