package com.example.pacientes.data_base;

import com.example.pacientes.get_set.LoginGetSet;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDb {

    public LoginGetSet Acesso(LoginGetSet login){
        String sql = "SELECT * FROM login WHERE user = ? AND hash = ?";

        //Passa parametros para Query
        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){
            //Executa Query
            pstm.setString(1, login.getUsuario());
            pstm.setString(2,login.getSenha());

            try (ResultSet rs = pstm.executeQuery()){
                if (rs.next()) {
                    //Retorna se teve resultado
                    LoginGetSet UsuarioEncontrado = new LoginGetSet();
                    UsuarioEncontrado.setId(rs.getInt("id_medico"));
                    UsuarioEncontrado.setUsuario(rs.getString("user"));
                    UsuarioEncontrado.setIdade(rs.getInt("idade"));
                    UsuarioEncontrado.setNivel(rs.getString("nivel"));
                    return UsuarioEncontrado;
                }
            }

        } catch (SQLException e) {
            System.err.println("Hove um erro no LoginDb Acesso" + e);
        }
        return null;
    }
}
