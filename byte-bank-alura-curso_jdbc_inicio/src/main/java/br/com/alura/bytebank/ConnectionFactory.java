package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
//import java.sql.DriverManager;

public class ConnectionFactory {

    public Connection recuperaConexao() {
        try {
//            return DriverManager.getConnection("jdbc:mysql://localhost/byte_bank?user=gabriel&password=Maca@001");
            return createDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Biblioteca que gerencia a quantidade de conexões com o banco (pool de conexões)
    private HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/byte_bank");
        config.setUsername("gabriel");
        config.setPassword("Maca@001");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
