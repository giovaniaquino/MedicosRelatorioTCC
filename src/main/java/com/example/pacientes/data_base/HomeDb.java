package com.example.pacientes.data_base;

import com.example.pacientes.get_set.HomeGetSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class HomeDb {

    public void CadastraPaciente(HomeGetSet paciente){
        String sql = "INSERT INTO paciente (Nome, Idade, Sexo, CPF) VALUES (?,?,?,?)";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, paciente.getPacienteNome());
            pstm.setString(2, paciente.getPacienteIdade());
            pstm.setString(3, paciente.getPacienteSexo());
            pstm.setString(4, paciente.getPacienteCpf());
            pstm.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
