package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.ConnectionFactory;
import br.com.alura.bytebank.domain.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Set;

public class ContaService {

    private ConnectionFactory conexao;

    public ContaService() {
        this.conexao = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        Connection conn = conexao.recuperaConexao();
        return new ContaDAO(conn).listar();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        Connection conn = conexao.recuperaConexao();
        new ContaDAO(conn).salvar(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        BigDecimal novoValor = conta.getSaldo().subtract(valor);
        alterar(conta, novoValor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        BigDecimal novoValor = conta.getSaldo().add(valor);
        alterar(conta, novoValor);
    }

    public void realizarTransferencia(Integer numeroDaContaOrigem, Integer numeroDaContaDestino, BigDecimal valor) {
        buscarContaPorNumero(numeroDaContaOrigem);
        buscarContaPorNumero(numeroDaContaDestino);

        this.realizarSaque(numeroDaContaOrigem, valor);
        this.realizarDeposito(numeroDaContaDestino, valor);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("A conta " + conta.getNumero() + " não pode ser encerrada pois ainda possui saldo!");
        }
        Connection conn = conexao.recuperaConexao();
        new ContaDAO(conn).desativar(conta.getNumero());
    }

    private Conta buscarContaPorNumero(Integer numero) {
        Connection conn = conexao.recuperaConexao();
        Conta conta = new ContaDAO(conn).buscar(numero);

        if (conta == null) {
            throw new RegraDeNegocioException("Não existe conta cadastrada com o número: " + numero + "!");
        } else {
            return conta;
        }
    }

    private void alterar(Conta conta, BigDecimal valor) {
        Connection conn = conexao.recuperaConexao();
        new ContaDAO(conn).alterar(conta.getNumero(), valor);
    }
}
