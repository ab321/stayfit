package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.ExercisePosition;
import com.example.stayfit.model.entity.Set;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.repository.ExercisePositionRepository;
import com.example.stayfit.model.repository.ExerciseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ExerciseListController extends stayfitController {

    @FXML
    private ListView<Exercise> exerciseListView;

    private ExercisePositionRepository exercisePositionRepository;

    public static Template selectedTemplate;

    @FXML
    private void initialize() {
        this.exercisePositionRepository = new ExercisePositionRepository();

        try {
            List<Exercise> exercises = exercisePositionRepository.findWithTemplateId(selectedTemplate.getId());
            ObservableList<Exercise> exerciseList = FXCollections.observableList(exercises);
            exerciseListView.setItems(exerciseList);

        } catch (SQLException e) {
            showErrorMessage("Error retrieving exercises", e.getMessage());
        }
    }


    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleDeleteButtonAction(ActionEvent actionEvent) {
        Exercise selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();

        if (selectedExercise != null) {
            ExercisePositionRepository exercisePositionRepository = new ExercisePositionRepository();
            try {
                exercisePositionRepository.deleteExerciseFromTemplate(selectedExercise.getId(), selectedTemplate.getId());
                exerciseListView.getItems().remove(selectedExercise);
            } catch (SQLException e) {
                showErrorMessage("Error deleting exercise", e.getMessage());
            }
        } else {
            showErrorMessage("ERROR", "No exercise has been selected.");
        }

    }

    public void onBtnAddToSet(ActionEvent actionEvent) throws IOException {
        Exercise exercise = exerciseListView.getSelectionModel().getSelectedItem();

        if (exercise != null) {
            java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());

            Set set = new Set(exercise,
                    currentUser,
                    selectedTemplate,
                    0.0,
                    date,
                    0);

            setCurrentSet(set);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stayfit/view/addToSet.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Set hinzufügen");
            stage.setScene(new Scene(root));
            stage.show();

        } else {
            showErrorMessage("ERROR", "No exercise has been selected.");
        }

    }
}
