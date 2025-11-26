package com.example.pacientes.data_base;

import com.example.pacientes.get_set.HomeGetSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDb {

    public HomeGetSet InfoPaciente(){
        String sql = "SELECT  * FROM paciente";
        HomeGetSet info = null;

        try (Connection conn = new ConnectionDb().connect();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery()){

            if (rs.next()) {
                info = new HomeGetSet();
                info.setPacienteNome(rs.getString("Nome"));
                info.setPacienteIdade(rs.getString("Idade"));
                info.setPacienteSexo(rs.getString("Sexo"));
                info.setPacienteCpf(rs.getString("CPF"));
            }

        }catch (SQLException e){
            System.err.println("Ocorreu um erro na Info Paciente: " + e.getMessage());
        }
        return info;
    }

    public void AtualizaPaciente(HomeGetSet paciente){
        String sql = "UPDATE paciente SET Nome = ?, Idade = ?, Sexo = ?, CPF = ?";

        try (Connection conexao = new ConnectionDb().connect();
             PreparedStatement pstm = conexao.prepareStatement(sql)){

            //Executa Query
            pstm.setString(1, paciente.getPacienteNome());
            pstm.setString(2, paciente.getPacienteIdade());
            pstm.setString(3, paciente.getPacienteSexo());
            pstm.setString(4, paciente.getPacienteCpf());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar paciente: "+e);
        }
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

    public ObservableList<HomeGetSet> EmocoesDias(String dataInicio, String dataFim) {
        ObservableList<HomeGetSet> listaDados = FXCollections.observableArrayList();

        String sql = "SELECT DATE(timestamp) AS dia, emotion, COUNT(*) AS contagem " +
                "FROM emotions " +
                "WHERE timestamp BETWEEN ? AND ? " +
                "GROUP BY dia, emotion " +
                "ORDER BY dia, emotion";

        try (Connection conn = new ConnectionDb().Samuel();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, dataInicio + " 00:00:00");
            pstm.setString(2, dataFim + " 23:59:59");

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    HomeGetSet data = new HomeGetSet();
                    data.EmocaoData(
                            rs.getString("dia"),
                            rs.getString("emotion"),
                            rs.getInt("contagem")
                    );

                    listaDados.add(data);
                }
            }

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL ao buscar dados de emoção dias: " + e.getMessage());
            e.printStackTrace();
        }

        return listaDados;
    }

    public ObservableList<HomeGetSet> EmocoesHoras(String dia) {
        ObservableList<HomeGetSet> listaDados = FXCollections.observableArrayList();

        String sql = "SELECT HOUR(timestamp) AS hora, emotion, COUNT(*) AS contagem " +
                "FROM emotions " +
                "WHERE DATE(timestamp) = ? " +
                "GROUP BY hora, emotion " +
                "ORDER BY hora, emotion";

        try (Connection conn = new ConnectionDb().Samuel();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, dia);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    HomeGetSet data = new HomeGetSet();
                    String horaFormatada = String.format("%02d:00", rs.getInt("hora"));

                    data.EmocaoData(
                            horaFormatada,
                            rs.getString("emotion"),
                            rs.getInt("contagem")
                    );

                    listaDados.add(data);
                }
            }

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL ao buscar dados de emoção horas: " + e.getMessage());
            e.printStackTrace();
        }

        return listaDados;
    }

    public ObservableList<HomeGetSet> EmocoesMinutos(String dia, int hora) {
        ObservableList<HomeGetSet> listaDados = FXCollections.observableArrayList();

        // Query para buscar por MINUTO dentro de uma HORA específica
        String sql = "SELECT MINUTE(timestamp) AS minuto, emotion, COUNT(*) AS contagem " +
                "FROM emotions " +
                "WHERE DATE(timestamp) = ? AND HOUR(timestamp) = ? " +
                "GROUP BY minuto, emotion " +
                "ORDER BY minuto, emotion";

        try (Connection conn = new ConnectionDb().Samuel();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, dia);
            pstm.setInt(2, hora);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    HomeGetSet data = new HomeGetSet();
                    String minutoFormatado = String.format("%02d:%02d", hora, rs.getInt("minuto"));

                    data.EmocaoData(
                            minutoFormatado,
                            rs.getString("emotion"),
                            rs.getInt("contagem")
                    );

                    listaDados.add(data);
                }
            }

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro de SQL ao buscar dados de emoção minutos: " + e.getMessage());
            e.printStackTrace();
        }

        return listaDados;
    }
}
