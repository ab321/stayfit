package com.example.stayfit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class stayfitApp extends Application {
    private static Stage currentStage = null;
    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(stayfitApp.class.getResource("login.fxml"));
        currentStage.getIcons().add(new Image(stayfitApp.class.getResourceAsStream("logo.jpg")));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        currentStage.setTitle("Stayfit");
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static Stage getCurrentStage(){
        return currentStage;
    }

    public static void main(String[] args) {
        launch();
    }
}