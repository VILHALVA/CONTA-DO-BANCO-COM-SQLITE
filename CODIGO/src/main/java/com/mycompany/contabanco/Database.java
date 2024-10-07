package com.mycompany.contabanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:src/main/java/com/mycompany/contabanco/contabanco.db"; 

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Conexão com o banco de dados SQLite estabelecida.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contas (" +
                     "numConta INTEGER PRIMARY KEY," +
                     "dono TEXT NOT NULL," +
                     "tipo TEXT NOT NULL," +
                     "saldo REAL NOT NULL," +
                     "status INTEGER NOT NULL" +
                     ");";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela de contas criada ou já existente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
