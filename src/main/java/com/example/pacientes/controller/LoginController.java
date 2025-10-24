package com.example.pacientes.controller;


import com.example.pacientes.data_base.LoginDb;
import com.example.pacientes.get_set.LoginGetSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    @FXML AnchorPane PaneLogin;
    @FXML TextField TfLogin;
    @FXML PasswordField PfSenha;
    @FXML Label LbErro;
    public static String Usuario, Id, Idade, Nivel;

    @FXML
    public void Entrar(){

        //Passa valores para o get e set
        LoginGetSet login = new LoginGetSet();
        login.setUsuario(TfLogin.getText());
        login.setSenha(PfSenha.getText());

        //Faz conexao com classe login_db
        LoginDb entra = new LoginDb();

        //Verifica retorno da Query
        LoginGetSet UsuarioAutenticado = entra.Acesso(login);
        if (UsuarioAutenticado != null) {
            //Salva dados do usuario acessado para display futuro
            Id = String.valueOf(UsuarioAutenticado.getId());
            Usuario = UsuarioAutenticado.getUsuario();
            Idade = String.valueOf(UsuarioAutenticado.getIdade());
            Nivel = UsuarioAutenticado.getNivel();

            //Acao do botao para entrar
            try {
                //Esconde LoginView
                PaneLogin.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/pacientes/HomeView.fxml"));
                Parent root = loader.load();

                //Configuracoes da tela
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.err.println("Erro ao chamar home_view " + e);
            }
        } else {
            // Credenciais erradas
            LbErro.setVisible(true);
        }
    }
}