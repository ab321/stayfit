module com.example.stayfit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.stayfit to javafx.fxml;
    exports com.example.stayfit;
    exports com.example.stayfit.controller;
    opens com.example.stayfit.controller to javafx.fxml;
    exports com.example.stayfit.model.repository;
    opens com.example.stayfit.model.repository to javafx.fxml;
    exports com.example.stayfit.model;
    opens com.example.stayfit.model to javafx.fxml;
    exports com.example.stayfit.model.entity;
    opens com.example.stayfit.model.entity to javafx.fxml;
}