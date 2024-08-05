package br.com.gabrielcalasans.loja.testes;

import br.com.gabrielcalasans.loja.dao.CategoriaDAO;
import br.com.gabrielcalasans.loja.dao.ClienteDAO;
import br.com.gabrielcalasans.loja.dao.PedidoDAO;
import br.com.gabrielcalasans.loja.dao.ProdutoDAO;
import br.com.gabrielcalasans.loja.modelo.*;
import br.com.gabrielcalasans.loja.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class CadastroDeProduto {

    public static void main(String[] args) {

//        cadastarProduto();

//        buscarProduto();

        EntityManager entityManager = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        Produto produto = produtoDAO.buscarPorNome("Iphone 15");

        entityManager.getTransaction().begin();

//        Cliente cliente = new Cliente("Gabriel", "12345678910");
//        Pedido pedido = new Pedido(cliente);
//        ClienteDAO clienteDAO = new ClienteDAO(entityManager);
//        clienteDAO.cadastrar(cliente);
//
//        pedido.adicionarItem(new ItemPedido(pedido, produto, 2));
//
        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);

        Pedido pedido = pedidoDAO.buscarPedidoComCliente(2L);

        entityManager.getTransaction().commit();

        // Se a boa prática estiver como lazy você pode ter problema para fazer essa consulta
        // Pois como o EntityManager está fechado, não irá retornar o Cliente junto no find direto
        // Nesse caso se faz necessário criar uma consulta que retorne tudo (buscarPedidoComCliente)
        System.out.println(pedido.getCliente().getDadosPessoais().getNome());
    }

    private static void buscarProduto() {
        // Cria o EntityManager para persistencia do objeto no banco de dados
        // A criação do Factory foi jogada para uma classe JPAUtil
        EntityManager entityManager = JPAUtil.getEntityManager();

        // Cria as classes CategoriaDAO ProdutoDAO
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);

        // Busca produto por id
        Produto produto = produtoDAO.buscarPorId(2L);
        Categoria categoria = categoriaDAO.buscarPorNome("smartfone");
        System.out.println(produto);
        System.out.println(categoria);

        // Busca produto por nome
        produto = produtoDAO.buscarPorNome("Iphone 15");
        System.out.println(produto);
    }

    private static void cadastarProduto() {
        // Cria um objeto da Classe Categoria
        Categoria celulares = new Categoria("smartfone");

        // Cria um objeto da class Produto
        // id é gerado automaticamente pelo banco de dados
        Produto produto = new Produto("Iphone 15", "Celular da marca Apple", new BigDecimal("99.99"), celulares);

        // Cria o EntityManager para persistencia do objeto no banco de dados
        // A criação do Factory foi jogada para uma classe JPAUtil
        EntityManager entityManager = JPAUtil.getEntityManager();

        // Cria as classes CategoriaDAO ProdutoDAO
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);

        // Cria a requisição para transação (pois: transaction-type="RESOURCE_LOCAL")
        // Com o JTA não precisa desse comando
        entityManager.getTransaction().begin();

        // Faz a inserção no banco
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(produto);

        // Sincroniza com o banco mas não salva ainda
        entityManager.flush();

        // Muda o estado para Detached
        entityManager.clear();

        // Retorna estado de Detached para Managed
        celulares = categoriaDAO.atualizar(celulares);

        // Faz alteração
        String nomeCategoria = "smartfone";
        if (categoriaDAO.buscarPorNome(nomeCategoria) == null) {
            celulares.setNome("smartfone");
        } else {
            System.out.println("Categoria com esse nome já consta no banco");
        }
        entityManager.flush();

        // Faz o commit da transação
        entityManager.getTransaction().commit();

        // Fecha a transação (muda estado para detached)
        entityManager.close();
    }

}
