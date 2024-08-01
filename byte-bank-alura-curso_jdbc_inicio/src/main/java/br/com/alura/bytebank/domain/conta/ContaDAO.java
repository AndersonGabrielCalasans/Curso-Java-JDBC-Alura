package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    ContaDAO(Connection connection) {
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta) {
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente, BigDecimal.ZERO, true);

        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email, esta_ativa)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Cria objeto que prepara a query pra transmissão no DB
            ps = conn.prepareStatement(sql);

            // Passa os parametros (?, ?, ?, ?, ?, ?) para a query
            ps.setInt(1, conta.getNumero());
            ps.setBigDecimal(2, conta.getSaldo());
            ps.setString(3, dadosDaConta.dadosCliente().nome());
            ps.setString(4, dadosDaConta.dadosCliente().cpf());
            ps.setString(5, dadosDaConta.dadosCliente().email());
            ps.setBoolean(6, true);

            // Realiza a query e popula o banco
            ps.execute();

            // Fecha conexão
            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar() {
        Set<Conta> contas = new HashSet<>();

        String sql = "SELECT * FROM conta";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Integer numero = rs.getInt(1);
                BigDecimal saldo = rs.getBigDecimal(2);
                String nome = rs.getString(3);
                String cpf = rs.getString(4);
                String email = rs.getString(5);
                Boolean estaAtiva = rs.getBoolean(6);
                DadosCadastroCliente cadastroCliente = new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(cadastroCliente);
                Conta conta = new Conta(numero, cliente, saldo, estaAtiva);
                if (conta.getEstaAtiva()) {
                    contas.add(conta);
                }
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contas;
    }

    public Conta buscar(Integer numero) {
        Conta conta = null;

        String sql = "SELECT * FROM conta WHERE conta.numero = ?;";

        try {
            // Cria a query com o numero da conta informada
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numero);
            rs = ps.executeQuery();

            // Passa as informações para a String
            while (rs.next()) {
                Integer numeroConta = rs.getInt(1);
                BigDecimal saldo = rs.getBigDecimal(2);
                String nome = rs.getString(3);
                String cpf = rs.getString(4);
                String email = rs.getString(5);
                Boolean estaAtiva = rs.getBoolean(6);
                DadosCadastroCliente cadastroCliente = new DadosCadastroCliente(nome, cpf, email);
                Cliente cliente = new Cliente(cadastroCliente);

                // Retorna conta do número digitado se estiver ativa
                if (estaAtiva) {
                    conta = new Conta(numeroConta, cliente, saldo, estaAtiva);
                }
            }

            // Fecha conexao com DB
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conta;
    }

    public void alterar(Integer numero, BigDecimal valor) {
        String sql = "UPDATE conta SET saldo = ? WHERE conta.numero = ?";

        try {
            // Commit = false para utilizar o roolback caso dê falha em algum passo
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            // Realiza a query e popula o banco
            ps.execute();
            // Realiza commit no DB
            conn.commit();

            // Fecha conexão
            ps.close();
            conn.close();
        } catch (SQLException e) {
            try {
                // Faz rollback caso falhe a alteração
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void desativar(Integer numero) {

//        String sql = "DELETE FROM conta WHERE numero = ?";
        String sql = "UPDATE conta SET esta_ativa = false WHERE conta.numero = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numero);
            ps.execute();

            // Fecha conexão
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ativar(Integer numero) {
        String sql = "UPDATE conta SET esta_ativa = true WHERE conta.numero = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numero);
            ps.execute();

            // Fecha conexão
            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
