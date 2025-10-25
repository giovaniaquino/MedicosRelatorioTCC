package com.example.pacientes.data_base;

import com.example.pacientes.get_set.HomeGetSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDb {

    public void CadastraPaciente(HomeGetSet paciente){
        String sql = "INSERT INTO paciente (Nome, Idade, Sexo, CPF, id_medico) VALUES (?,?,?,?,?)";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, paciente.getPacienteNome());
            pstm.setString(2, paciente.getPacienteIdade());
            pstm.setString(3, paciente.getPacienteSexo());
            pstm.setString(4, paciente.getPacienteCpf());
            pstm.setInt(5,paciente.getMedicoId());
            pstm.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<HomeGetSet> ListaPacientes(HomeGetSet medico){
        ObservableList<HomeGetSet> lista = FXCollections.observableArrayList();
        String sql = "SELECT Nome, Idade, Sexo, CPF FROM paciente WHERE id_medico = ?";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            pstm.setInt(1, medico.getMedicoId());

            try (ResultSet rs = pstm.executeQuery()){
                while (rs.next()) {

                    HomeGetSet pacientes = new HomeGetSet();
                    pacientes.setPacienteNome(rs.getString("Nome"));
                    pacientes.setPacienteIdade(rs.getString("Idade"));
                    pacientes.setPacienteSexo(rs.getString("Sexo"));
                    pacientes.setPacienteCpf(rs.getString("CPF"));

                    lista.add(pacientes);
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro no Result Set da Lista Pacientes: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL Lista Pacientes: " + e.getMessage());
        }
        return lista;
    }
}
