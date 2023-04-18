package com.example.stayfit.controller;

import com.example.stayfit.stayfitApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class stayfitController {

    public void onBtnLogin(ActionEvent actionEvent) throws IOException {
        Stage stage = stayfitApp.getCurrentStage();
        FXMLLoader fxmlLoader = new FXMLLoader(stayfitApp.class.getResource("view/template.fxml"));
        stage.getIcons().add(new Image(stayfitApp.class.getResourceAsStream("images/logo.jpg")));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Stayfit");
        stage.setScene(scene);
        stage.show();
    }
}