package br.com.gabrielcalasans.loja.dao;

import br.com.gabrielcalasans.loja.modelo.Pedido;
import br.com.gabrielcalasans.loja.modelo.Produto;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class PedidoDAO {

    private EntityManager entityManager;

    public PedidoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Pedido pedido) {
        this.entityManager.persist(pedido);
//        atualizarTotal(pedido);
    }

//    public void atualizarTotal(Pedido pedido) {
//        // Consulta para calcular o valor total do pedido
//        String jpql = "SELECT SUM(ip.precoUnitario * ip.quantidade) FROM ItemPedido ip WHERE ip.pedido.id = :id";
//        BigDecimal valorTotal = (BigDecimal) entityManager.createQuery(jpql, BigDecimal.class)
//                .setParameter("id", pedido.getId())
//                .getSingleResult();
//
//        // Atualiza o valor total do pedido
//        pedido.setValorTotal(valorTotal);
//        jpql = "UPDATE Pedido p SET p.valorTotal = :valorTotal WHERE p.id = :id";
//        entityManager.createQuery(jpql)
//                .setParameter("valorTotal", valorTotal)
//                .setParameter("id", pedido.getId())
//                .executeUpdate();
//    }

    // Faz o carregamento da consulta trazendo o Cliente (carregamento Eager planejado)
    public Pedido buscarPedidoComCliente(Long id) {
        return entityManager.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
