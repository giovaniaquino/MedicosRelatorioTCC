package com.example.pacientes.controller;

import com.example.pacientes.Hash;
import com.example.pacientes.data_base.HomeDb;
import com.example.pacientes.get_set.HomeGetSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class HomeController {

    @FXML BorderPane MainPane;
    @FXML AnchorPane PaneMedico, PanePaciente, PaneRelatorio, PaneAddMedico;
    @FXML Label LbBemVindo, MedicoId, MedicoNome,MedicoIdade, MedicoQuantPaciente, LbMedicoSenha, LbMedicoNovaSenha, LbMedicoConfSenha, LbMedicoErro;
    @FXML Button BtMedico, BtPaciente, BtRelatorio, BtAddMedico, BtMedicoConfirma;
    @FXML TextField TfPacienteNome, TfPacienteCpf, TfPacienteIdade, TfMedicoNome, TfMedicoIdade;
    @FXML PasswordField PfMedicoSenha, PfMedicoNovaSenha, PfMedicoConfSenha;
    @FXML ChoiceBox<String> CbPacienteSexo, CbRelatorio;
    @FXML TableView<HomeGetSet> TbPaciente, TbMedico;
    @FXML TableColumn<HomeGetSet, String> ClPacienteNome, ClPacienteSexo, ClPacienteCpf, ClMedicoNome;
    @FXML TableColumn<HomeGetSet, Integer> ClPacienteIdade, ClMedicoId;
    @FXML LineChart<String, Number> LineChart;
    private XYChart.Series<String, Number> FelizSeries;
    private XYChart.Series<String, Number> TristeSeries;
    private XYChart.Series<String, Number> NeutroSeries;
    private ObservableList<PieChart.Data> PieChartData;
    @FXML PieChart PieChart;
    @FXML DatePicker DpDataInicio;
    @FXML DatePicker DpDataFim;
    private int Id;

    private HomeDb homeDb;


    @FXML
    private void initialize(){
        CbPacienteSexo.getItems().setAll("M","F");

        //Esconder botao de cadastro de medico para usuarios nao administradores
        if (LoginController.Nivel.equals("medico")){
            BtAddMedico.setVisible(false);
        }

        this.homeDb = new HomeDb(); // Instancia o DB helper
        InicializarGrafico();
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

        //Limpa campos após cadastro
        TfPacienteNome.setText("");
        TfPacienteIdade.setText("");
        TfPacienteCpf.setText("");
        CbPacienteSexo.setValue(null);

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

        //Limpa campos após cadastro
        TfMedicoNome.setText("");
        TfMedicoIdade.setText("");

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

            //Limpa campos após cadastro
            TfMedicoNome.setText("");
            TfMedicoIdade.setText("");
            PfMedicoSenha.setText("");
            PfMedicoConfSenha.setText("");
            PfMedicoNovaSenha.setText("");

            //Esconde campos de alterar senha
            LbMedicoNovaSenha.setVisible(false);
            PfMedicoNovaSenha.setVisible(false);
            LbMedicoConfSenha.setVisible(false);
            PfMedicoConfSenha.setVisible(false);
            BtMedicoConfirma.setVisible(false);

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
    private void SelecionaPaciente(){

        HomeGetSet medico = new HomeGetSet();
        medico.setMedicoId(Integer.parseInt(LoginController.Id));

        HomeDb paciente = new HomeDb();

        ObservableList<String> listaDeNomes = paciente.ListaNomePaciente(medico);
        CbRelatorio.getItems().setAll(listaDeNomes);
    }

    private void InicializarGrafico() {
        //Criar as Séries (linhas)
        FelizSeries = new XYChart.Series<>();
        FelizSeries.setName("Feliz");

        TristeSeries = new XYChart.Series<>();
        TristeSeries.setName("Triste");

        NeutroSeries = new XYChart.Series<>();
        NeutroSeries.setName("Neutro");

        //Adiciona as séries no gráfico
        if (LineChart != null) {
            LineChart.getData().addAll(FelizSeries, TristeSeries, NeutroSeries);
            LineChart.setAnimated(false);
        }

        //Inicializa a lista de dados da pizza com valores zerados
        PieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Feliz", 0),
                new PieChart.Data("Triste", 0),
                new PieChart.Data("Neutro", 0)
        );

        // Associa a lista de dados ao gráfico de pizza
        if (PieChart != null) {
            PieChart.setData(PieChartData);
            PieChart.setTitle("Total no Período");
            PieChart.setLabelsVisible(false);
            

        }

        //Definir datas padrão para os DatePickers
        if (DpDataFim != null && DpDataInicio != null) {
            DpDataFim.setValue(LocalDate.now());
            DpDataInicio.setValue(LocalDate.now().minusDays(30)); // Padrão: últimos 30 dias
        }
    }

    //Chamado por botao atualizar
    @FXML
    private void AtualizarGraficoAction() {
        LocalDate inicio = DpDataInicio.getValue();
        LocalDate fim = DpDataFim.getValue();

        if (inicio == null || fim == null) {
            // Mostrar um alerta para o usuário
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, selecione data de início e fim.");
            alert.showAndWait();
            return;
        }

        // Converte LocalDate para String no formato 'YYYY-MM-DD'
        String dataInicioStr = inicio.toString();
        String dataFimStr = fim.toString();

        // O Task para executar a consulta em background
        Task<ObservableList<HomeGetSet>> loadDataTask = new Task<>() {
            @Override
            protected ObservableList<HomeGetSet> call() throws Exception {
                // Esta linha executa em uma THREAD SEPARADA
                return homeDb.Emocoes(dataInicioStr, dataFimStr);
            }
        };

        //O que fazer QUANDO a task terminar com SUCESSO
        loadDataTask.setOnSucceeded(e -> {
            // Esta parte é executada de volta na THREAD DO JAVAFX
            ObservableList<HomeGetSet> dados = loadDataTask.getValue();
            processarDadosDoGrafico(dados);
            processarDadosGraficoPizza(dados);
        });

        //O que fazer se a task FALHAR
        loadDataTask.setOnFailed(e -> {
            // Mostrar erro
            loadDataTask.getException().printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar dados do gráfico: " + loadDataTask.getException().getMessage());
            alert.showAndWait();
        });

        // 5. Inicia a task!
        new Thread(loadDataTask).start();
    }

    private void processarDadosDoGrafico(ObservableList<HomeGetSet> dados) {
        // Listas temporárias para armazenar os pontos
        ObservableList<XYChart.Data<String, Number>> FelizData = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> TristeData = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> NeutroData = FXCollections.observableArrayList();

        // Itera sobre os resultados já agregados
        for (HomeGetSet item : dados) {
            String dia = item.getDia();
            int contagem = item.getContagem();

            switch (item.getEmocao()) {
                case "happy":
                    FelizData.add(new XYChart.Data<>(dia, contagem));
                    break;
                case "sad":
                    TristeData.add(new XYChart.Data<>(dia, contagem));
                    break;
                case "neutral":
                    NeutroData.add(new XYChart.Data<>(dia, contagem));
                    break;
            }
        }

        // Limpa os dados antigos ANTES de adicionar os novos
        FelizSeries.getData().clear();
        TristeSeries.getData().clear();
        NeutroSeries.getData().clear();

        //Adiciona os novos dados de uma vez
        FelizSeries.getData().addAll(FelizData);
        TristeSeries.getData().addAll(TristeData);
        NeutroSeries.getData().addAll(NeutroData);

        //Desabilita símbolos (pontos) se houver muitos dados
        LineChart.setCreateSymbols(dados.size() < 100); // Só mostra pontos se houver menos de 100 dados totais
    }

    private void processarDadosGraficoPizza(ObservableList<HomeGetSet> dados) {
        //Zera os contadores
        long totalFeliz = 0;
        long totalTriste = 0;
        long totalNeutro = 0;

        //Itera sobre a lista de dados e soma os totais
        for (HomeGetSet item : dados) {
            switch (item.getEmocao()) {
                case "happy":
                    totalFeliz += item.getContagem();
                    break;
                case "sad":
                    totalTriste += item.getContagem();
                    break;
                case "neutral":
                    totalNeutro += item.getContagem();
                    break;
            }
        }

        //Atualiza os valores do gráfico
        for (PieChart.Data slice : PieChartData) {
            switch (slice.getName()) {
                case "Feliz":
                    slice.setPieValue(totalFeliz);
                    break;
                case "Triste":
                    slice.setPieValue(totalTriste);
                    break;
                case "Neutro":
                    slice.setPieValue(totalNeutro);
                    break;
            }
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

            SelecionaPaciente();

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
