package com.example.pacientes.data_base;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {

    //Classe de conexao
    public java.sql.Connection connect() {
        java.sql.Connection conecction = null;
        try {
            // Informações do banco de dados
            String url = "jdbc:mysql://localhost:3306/medico";
            String user = "root";
            String senha = "root";
            conecction = DriverManager.getConnection(url, user, senha);

        } catch (SQLException e) {
            System.err.println("Erro na conexão com o banco de dados: " + e.getMessage());
        }
        return conecction;
    }

}
