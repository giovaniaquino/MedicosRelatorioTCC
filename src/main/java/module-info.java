module com.example.pacientes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pacientes to javafx.fxml;
    exports com.example.pacientes;
    exports com.example.pacientes.controller;
    opens com.example.pacientes.controller to javafx.fxml;
}