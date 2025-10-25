package com.example.pacientes.controller;

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

public class HomeController {

    @FXML BorderPane MainPane;
    @FXML AnchorPane PaneMedico, PanePaciente, PaneRelatorio, PaneAddMedico;
    @FXML Label LbBemVindo, MedicoId, MedicoNome,MedicoIdade, MedicoQuantPaciente;
    @FXML Button BtMedico, BtPaciente, BtRelatorio, BtAddMedico;
    @FXML TextField TfPacienteNome, TfPacienteCpf, TfPacienteIdade;
    @FXML ChoiceBox<String> CbPacienteSexo;
    @FXML TableView<HomeGetSet> TbPaciente;
    @FXML TableColumn<HomeGetSet, String> ClPacienteNome, ClPacienteSexo, ClPacienteCpf;
    @FXML TableColumn<HomeGetSet, Integer> ClPacienteIdade;


    @FXML
    private void initialize(){
        CbPacienteSexo.getItems().setAll("M","F");
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
}
