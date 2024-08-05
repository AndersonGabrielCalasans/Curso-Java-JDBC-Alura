package br.com.gabrielcalasans.loja.dao;

import br.com.gabrielcalasans.loja.modelo.Cliente;
import br.com.gabrielcalasans.loja.modelo.Pedido;
import jakarta.persistence.EntityManager;

public class ClienteDAO {

    private EntityManager entityManager;

    public ClienteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Cliente cliente) {
        this.entityManager.persist(cliente);
    }
}
