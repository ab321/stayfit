package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Set;
import com.example.stayfit.model.entity.User;
import com.example.stayfit.stayfitApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

abstract class stayfitController {
    protected static User currentUser;
    protected static Set currentSet;

    protected void getNewStage(String name) throws IOException {
        Stage stage = stayfitApp.getCurrentStage();
        FXMLLoader fxmlLoader = new FXMLLoader(stayfitApp.class.getResource("view/" + name + ".fxml"));
        stage.getIcons().add(new Image(stayfitApp.class.getResourceAsStream("images/logo.jpg")));

        Scene scene = new Scene(fxmlLoader.load(), 700, 450);

        stage.setTitle("Stayfit");
        stage.setScene(scene);
        stage.show();
    }

    public void onBtnLogout(ActionEvent actionEvent) throws IOException {
        getNewStage("login");
    }

    public void onBtnTemplate(ActionEvent actionEvent) throws IOException {
        getNewStage("template");
    }

    public void onBtnSet(ActionEvent actionEvent) throws IOException {
        getNewStage("set");
    }

    public void onBtnExercise(ActionEvent actionEvent) throws IOException {
        getNewStage("exercise");
    }


    public void onBtnCalculator(ActionEvent actionEvent) throws IOException {
        getNewStage("calculator");
    }

    protected void createAlertAndShow(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setCurrentSet(Set currentSet) {
        this.currentSet = currentSet;
    }

    public Set getCurrentSet() {
        return currentSet;
    }

}