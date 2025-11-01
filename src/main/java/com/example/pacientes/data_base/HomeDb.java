package com.example.pacientes.data_base;

import com.example.pacientes.get_set.HomeGetSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDb {

    public String ContaPacientes(HomeGetSet medico){
        String sql = "SELECT COUNT(*) FROM paciente WHERE id_medico = ?";

        try (Connection conn = new ConnectionDb().connect();
        PreparedStatement pstm = conn.prepareStatement(sql)){

            pstm.setInt(1, medico.getMedicoId());

            try(ResultSet rs = pstm.executeQuery()){
                if (rs.next()){
                    return String.valueOf(rs.getInt("COUNT(*)"));
                }
            }

        }catch (SQLException e){
            System.err.println("Ocorreu um erro de Busca Quantidade Pacientes: " + e.getMessage());
        }
        return "0";
    }

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

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL Cadastra Paciente: " + e.getMessage());
        }
    }

    public void AtualizaPaciente(HomeGetSet paciente){
        String sql = "UPDATE paciente SET Nome = ?, Idade = ?, Sexo = ?, CPF = ? where id_paciente = ?";

        try (Connection conexao = new ConnectionDb().connect();
             PreparedStatement pstm = conexao.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, paciente.getPacienteNome());
            pstm.setString(2, paciente.getPacienteIdade());
            pstm.setString(3, paciente.getPacienteSexo());
            pstm.setString(4, paciente.getPacienteCpf());
            pstm.setInt(5,paciente.getPacienteId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar paciente: "+e);
        }
    }

    public ObservableList<HomeGetSet> ListaPacientes(HomeGetSet medico){
        ObservableList<HomeGetSet> lista = FXCollections.observableArrayList();
        String sql = "SELECT id_paciente, Nome, Idade, Sexo, CPF FROM paciente WHERE id_medico = ?";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            pstm.setInt(1, medico.getMedicoId());

            try (ResultSet rs = pstm.executeQuery()){
                while (rs.next()) {

                    HomeGetSet pacientes = new HomeGetSet();

                    pacientes.setPacienteId(rs.getInt("id_paciente"));
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

    public void CadastraMedico(HomeGetSet medico){
        String sql = "INSERT INTO login (user, hash, idade, nivel) VALUES (?,?,?,1)";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, medico.getMedicoNome());
            pstm.setString(2, medico.getMedicoSenha());
            pstm.setString(3, medico.getMedicoIdade());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL Cadastra Medico: " + e.getMessage());
        }
    }

    public void AtualizaMedico(HomeGetSet medico){
        String sql = "UPDATE login SET user = ?, idade = ? where id_medico = ?";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, medico.getMedicoNome());
            pstm.setString(2, medico.getMedicoIdade());
            pstm.setInt(3,medico.getMedicoId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar medico: "+e);
        }
    }

    public void TrocaSenha(HomeGetSet medico){
        String sql = "UPDATE login SET hash = ? where id_medico = ?";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, medico.getMedicoSenha());
            pstm.setInt(2,medico.getMedicoId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao Trocar Senha: "+e);
        }
    }

    public ObservableList<HomeGetSet> ListaMedicos(){
        ObservableList<HomeGetSet> lista = FXCollections.observableArrayList();
        String sql = "SELECT id_medico, user, idade FROM login";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()){

            while (rs.next()) {

                HomeGetSet medico = new HomeGetSet();
                medico.setMedicoId(rs.getInt("id_medico"));
                medico.setMedicoNome(rs.getString("user"));
                medico.setMedicoIdade(rs.getString("idade"));

                lista.add(medico);
            }
        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL Lista Pacientes: " + e.getMessage());
        }
        return lista;
    }

    public ObservableList<String> ListaNomePaciente(HomeGetSet medico){
        String sql = "SELECT Nome FROM paciente WHERE id_medico = ?";

        //Lista vazia para armazenar os nomes
        ObservableList<String> nomesPacientes = FXCollections.observableArrayList();

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            pstm.setInt(1, medico.getMedicoId());

            try (ResultSet rs = pstm.executeQuery()){
                while (rs.next()) {
                    nomesPacientes.add(rs.getString("Nome"));
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro no Result Set da Lista Pacientes para Relatorio: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao Listar Pacientes para Relatorio: "+e);
        }
        return nomesPacientes;
    }

    public ObservableList<HomeGetSet> Emocoes(String dataInicio, String dataFim) {
        ObservableList<HomeGetSet> listaDados = FXCollections.observableArrayList();

        String sql = "SELECT DATE(data) AS dia, emocoes, COUNT(*) AS contagem " +
                "FROM captura " +
                "WHERE data BETWEEN ? AND ? " +
                "GROUP BY dia, emocoes " +
                "ORDER BY dia, emocoes";

        try (Connection conn = new ConnectionDb().connect();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, dataInicio + " 00:00:00");
            pstm.setString(2, dataFim + " 23:59:59");

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    HomeGetSet data = new HomeGetSet();
                    data.EmocaoData(
                            rs.getString("dia"),
                            rs.getString("emocoes"),
                            rs.getInt("contagem")
                    );

                    listaDados.add(data);
                }
            }

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL ao buscar dados de emoção: " + e.getMessage());
            e.printStackTrace(); // É bom ver o stack trace para erros complexos
        }

        return listaDados;
    }
}
