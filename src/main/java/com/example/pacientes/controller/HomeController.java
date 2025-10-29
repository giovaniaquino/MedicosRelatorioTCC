package com.example.pacientes.controller;

import com.example.pacientes.Hash;
import com.example.pacientes.data_base.HomeDb;
import com.example.pacientes.get_set.HomeGetSet;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;

public class HomeController {

    @FXML BorderPane MainPane;
    @FXML AnchorPane PaneMedico, PanePaciente, PaneRelatorio, PaneAddMedico;
    @FXML Label LbBemVindo, MedicoId, MedicoNome,MedicoIdade, MedicoQuantPaciente, LbMedicoSenha, LbMedicoNovaSenha, LbMedicoConfSenha, LbMedicoErro;
    @FXML Button BtMedico, BtPaciente, BtRelatorio, BtAddMedico, BtMedicoConfirma;
    @FXML TextField TfPacienteNome, TfPacienteCpf, TfPacienteIdade, TfMedicoNome, TfMedicoIdade;
    @FXML PasswordField PfMedicoSenha, PfMedicoNovaSenha, PfMedicoConfSenha;
    @FXML ChoiceBox<String> CbPacienteSexo;
    @FXML TableView<HomeGetSet> TbPaciente, TbMedico;
    @FXML TableColumn<HomeGetSet, String> ClPacienteNome, ClPacienteSexo, ClPacienteCpf, ClMedicoNome;
    @FXML TableColumn<HomeGetSet, Integer> ClPacienteIdade, ClMedicoId;
    private int Id;


    @FXML
    private void initialize(){
        CbPacienteSexo.getItems().setAll("M","F");

        //Esconder botao de cadastro de medico para usuarios nao administradores
        if (LoginController.Nivel.equals("medico")){
            BtAddMedico.setVisible(false);
        }
    }

    @FXML
    private void InfoMedico(){
        //Mostrar informacoes do medico que acessou o sistema
        MedicoId.setText("ID: "+LoginController.Id);
        MedicoNome.setText("Nome: "+LoginController.Usuario);
        MedicoIdade.setText("Idade: "+LoginController.Idade);

        HomeGetSet medico = new HomeGetSet();
        medico.setMedicoId(Integer.parseInt(LoginController.Id));

        HomeDb busca = new HomeDb();
        MedicoQuantPaciente.setText("Quantidade de Pacientes: "+ busca.ContaPacientes(medico));
    }

    @FXML
    private void CadastraPaciente(){

        //Passa valores para o get e set
        HomeGetSet paciente = new HomeGetSet();
        paciente.setPacienteNome(TfPacienteNome.getText());
        paciente.setPacienteIdade(TfPacienteIdade.getText());
        paciente.setPacienteSexo(CbPacienteSexo.getValue());
        paciente.setPacienteCpf(TfPacienteCpf.getText());
        paciente.setMedicoId(Integer.parseInt(LoginController.Id));

        //Faz conexao com classe HomeDb e chama funcao de cadastro
        HomeDb cadastro = new HomeDb();
        cadastro.CadastraPaciente(paciente);

        //Limpa campos após cadastro
        TfPacienteNome.setText("");
        TfPacienteIdade.setText("");
        TfPacienteCpf.setText("");
        CbPacienteSexo.setValue(null);

        //Atualiza tabela de pacientes
        ListaPacientes();
    }

    @FXML
    private void AtualizaPaciente(){
        //Passa valores para o get e set
        HomeGetSet paciente = new HomeGetSet();
        paciente.setPacienteId(Id);
        paciente.setPacienteNome(TfPacienteNome.getText());
        paciente.setPacienteIdade(TfPacienteIdade.getText());
        paciente.setPacienteSexo(CbPacienteSexo.getValue());
        paciente.setPacienteCpf(TfPacienteCpf.getText());

        //Faz conexao com classe home_db
        HomeDb atualiza = new HomeDb();
        atualiza.AtualizaPaciente(paciente);

        ListaPacientes();
    }

    @FXML
    private void ListaPacientes() {
        //adiciona a tabela os pacientes do medico
        try {
            HomeDb busca = new HomeDb();

            HomeGetSet medico = new HomeGetSet();
            medico.setMedicoId(Integer.parseInt(LoginController.Id));

            ObservableList<HomeGetSet> lista = busca.ListaPacientes(medico);
            ClPacienteNome.setCellValueFactory(new PropertyValueFactory<>("PacienteNome"));
            ClPacienteIdade.setCellValueFactory(new PropertyValueFactory<>("PacienteIdade"));
            ClPacienteSexo.setCellValueFactory(new PropertyValueFactory<>("PacienteSexo"));
            ClPacienteCpf.setCellValueFactory(new PropertyValueFactory<>("PacienteCpf"));
            TbPaciente.setItems(lista);

        } catch (Exception e) {
            System.err.println("Ocorreu um erro na Tabela Pacientes: " + e.getMessage());
        }
    }

    @FXML
    private void CadastraMedico() throws NoSuchAlgorithmException {

        //Passa valores para o get e set
        HomeGetSet medico = new HomeGetSet();
        medico.setMedicoNome(TfMedicoNome.getText());
        medico.setMedicoIdade(TfMedicoIdade.getText());
        medico.setMedicoSenha(Hash.Encriptar(PfMedicoSenha.getText()));

        //Faz conexao com classe HomeDb e chama funcao de cadastro
        HomeDb cadastro = new HomeDb();
        cadastro.CadastraMedico(medico);

        //Limpa campos após cadastro
        TfMedicoNome.setText("");
        TfMedicoIdade.setText("");
        PfMedicoSenha.setText("");

        ListaMedico();

    }

    @FXML
    private void AtualizaMedico(){
        //Passa valores para o get e set
        HomeGetSet medico = new HomeGetSet();
        medico.setMedicoId(Id);
        medico.setMedicoNome(TfMedicoNome.getText());
        medico.setMedicoIdade(TfMedicoIdade.getText());

        //Faz conexao com classe home_db
        HomeDb atualiza = new HomeDb();
        atualiza.AtualizaMedico(medico);

        ListaMedico();
    }

    @FXML
    private void ListaMedico() {
        //adiciona a tabela os pacientes do medico
        try {
            HomeDb busca = new HomeDb();

            ObservableList<HomeGetSet> lista = busca.ListaMedicos();
            ClMedicoId.setCellValueFactory(new PropertyValueFactory<>("MedicoId"));
            ClMedicoNome.setCellValueFactory(new PropertyValueFactory<>("MedicoNome"));
            TbMedico.setItems(lista);

        } catch (Exception e) {
            System.err.println("Ocorreu um erro na Tabela Atendimentos: " + e.getMessage());
        }
    }

    @FXML
    private void MudaSenha() throws NoSuchAlgorithmException {
        if (PfMedicoNovaSenha.getText().equals(PfMedicoConfSenha.getText())) {
            HomeGetSet medico = new HomeGetSet();
            medico.setMedicoId(Id);
            medico.setMedicoSenha(Hash.Encriptar(PfMedicoConfSenha.getText()));

            //Faz conexao com classe home_db
            HomeDb atualiza = new HomeDb();
            atualiza.TrocaSenha(medico);

            LbMedicoErro.setVisible(false);
            ListaMedico();
        }else{
            LbMedicoErro.setVisible(true);
        }
    }

    @FXML
    private void TrocaSenha(){
        LbMedicoNovaSenha.setVisible(true);
        PfMedicoNovaSenha.setVisible(true);
        LbMedicoConfSenha.setVisible(true);
        PfMedicoConfSenha.setVisible(true);
        BtMedicoConfirma.setVisible(true);
    }

    @FXML
    private void TrocarPane(ActionEvent event) {
        //Esconde mensagem do inicio
        LbBemVindo.setVisible(false);

        //Classe para trocar telas
        if (event.getSource() == BtMedico) {
            PaneMedico.setVisible(true);
            PanePaciente.setVisible(false);
            PaneRelatorio.setVisible(false);
            PaneAddMedico.setVisible(false);

            InfoMedico();

        } else if (event.getSource() == BtPaciente) {
            PaneMedico.setVisible(false);
            PanePaciente.setVisible(true);
            PaneRelatorio.setVisible(false);
            PaneAddMedico.setVisible(false);

            //Atualiza tabela de pacientes
            ListaPacientes();

        } else if (event.getSource() == BtRelatorio) {
            PaneMedico.setVisible(false);
            PanePaciente.setVisible(false);
            PaneRelatorio.setVisible(true);
            PaneAddMedico.setVisible(false);

        } else if (event.getSource() == BtAddMedico) {
            PaneMedico.setVisible(false);
            PanePaciente.setVisible(false);
            PaneRelatorio.setVisible(false);
            PaneAddMedico.setVisible(true);

            ListaMedico();

        } else {
            System.out.println("Erro na troca de telas");
        }
    }

    public void sair(){
        System.exit(0);
    }

    public void minimizar(){
        Stage stage = (Stage) MainPane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void CliqueMouse(){
        if (PanePaciente.isVisible()) {
            try {
                HomeGetSet selecao = TbPaciente.getSelectionModel().getSelectedItem();
                TfPacienteNome.setText(selecao.getPacienteNome());
                TfPacienteIdade.setText(selecao.getPacienteIdade());
                TfPacienteCpf.setText(selecao.getPacienteCpf());
                CbPacienteSexo.setValue(selecao.getPacienteSexo());

                Id = selecao.getPacienteId();

            } catch (Exception e) {
                System.err.println("Ocorreu um erro Clique Mouse na tabela pacientes: " + e.getMessage());
            }
        }else if (PaneAddMedico.isVisible()){
            try {

                HomeGetSet selecao = TbMedico.getSelectionModel().getSelectedItem();
                TfMedicoNome.setText(selecao.getMedicoNome());
                TfMedicoIdade.setText(selecao.getMedicoIdade());

                Id = selecao.getMedicoId();

                PfMedicoSenha.setVisible(false);
                LbMedicoSenha.setVisible(false);

            } catch (Exception e) {
                System.err.println("Ocorreu um erro Clique Mouse na tabela medico: " + e.getMessage());
            }
        }
    }
}
