package com.example.pacientes.controller;

import com.example.pacientes.data_base.HomeDb;
import com.example.pacientes.get_set.HomeGetSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

    @FXML BorderPane MainPane;
    @FXML AnchorPane PaneMedico, PanePaciente, PaneRelatorio, PaneAddMedico;
    @FXML Label LbBemVindo, MedicoId, MedicoNome,MedicoIdade;
    @FXML Button BtMedico, BtPaciente, BtRelatorio, BtAddMedico;
    @FXML TextField TfPacienteNome, TfPacienteCpf, TfPacienteIdade;
    @FXML ChoiceBox<String> CbPacienteSexo;


    @FXML
    private void initialize(){
        CbPacienteSexo.getItems().setAll("M","F");
    }

    @FXML
    private void InfoMedico(){
        //Mostrar informacoes do medico que acessou o sistema
        MedicoId.setText(MedicoId.getText()+LoginController.Id);
        MedicoNome.setText(MedicoNome.getText()+LoginController.Usuario);
        MedicoIdade.setText(MedicoIdade.getText()+LoginController.Idade);
    }

    @FXML
    private void CadastraPaciente(){

        //Passa valores para o get e set
        HomeGetSet paciente = new HomeGetSet();
        paciente.setPacienteNome(TfPacienteNome.getText());
        paciente.setPacienteIdade(TfPacienteIdade.getText());
        paciente.setPacienteSexo(CbPacienteSexo.getValue());
        paciente.setPacienteCpf(TfPacienteCpf.getText());

        //Faz conexao com classe HomeDb e chama funcao de cadastro
        HomeDb cadastro = new HomeDb();
        cadastro.CadastraPaciente(paciente);

        //Limpa campos após cadastro
        TfPacienteNome.setText("");
        TfPacienteIdade.setText("");
        TfPacienteCpf.setText("");
        CbPacienteSexo.setValue(null);

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
