package com.example.stayfit;

import com.example.stayfit.model.Database;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class stayfitApp extends Application {
    private static Stage currentStage = null;
    private static Connection connection = null;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(stayfitApp.class.getResource("view/login.fxml"));
        currentStage.getIcons().add(new Image(stayfitApp.class.getResourceAsStream("images/logo.jpg")));

        Scene scene = new Scene(fxmlLoader.load(), 700, 450);

        currentStage.setTitle("Stayfit");
        currentStage.setScene(scene);
        currentStage.show();

        connection = Database.openConnection();

        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Database.closeConnection();
            }
        });
    }

    public static Connection getConnection(){
        return connection;
    }

    public static Stage getCurrentStage(){
        return currentStage;
    }

    public static void main(String[] args) {
        launch();
    }
}