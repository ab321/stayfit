package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.repository.ExerciseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.List;

public class ExerciseListController {
    @FXML
    private ListView<Exercise> exerciseListView;

    private ExerciseRepository exerciseRepository;

    @FXML
    private void initialize() {
        this.exerciseRepository = new ExerciseRepository();

        try {
            List<Exercise> exercises = exerciseRepository.getExercises();
            ObservableList<Exercise> exerciseList = FXCollections.observableArrayList(exercises);
            exerciseListView.setItems(exerciseList);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Abrufen der Übungen", e.getMessage());
        }
    }


    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
