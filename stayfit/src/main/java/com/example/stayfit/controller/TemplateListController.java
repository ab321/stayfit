package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.ExercisePosition;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.entity.User;
import com.example.stayfit.model.repository.ExercisePositionRepository;
import com.example.stayfit.model.repository.ExerciseRepository;
import com.example.stayfit.model.repository.TemplateRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;

import static com.example.stayfit.controller.stayfitController.currentUser;

public class TemplateListController {
    @FXML
    private ListView<Template> templateListView;

    @FXML
    private Button saveButton;

    private TemplateRepository templateRepository;
    private ExerciseRepository exerciseRepository;
    private ExercisePositionRepository exercisePositionRepository;

    @FXML
    private void initialize() {
        this.templateRepository = new TemplateRepository(currentUser);
        this.exerciseRepository = new ExerciseRepository();
        this.exercisePositionRepository = new ExercisePositionRepository();


        try {
            List<Template> templates = templateRepository.getTemplates();
            ObservableList<Template> templateList = FXCollections.observableArrayList(templates);
            templateListView.setItems(templateList);
        } catch (SQLException e) {
            showErrorMessage("Fehler beim Abrufen der Vorlagen", e.getMessage());
        }
    }

    public static Exercise selectedExercise;

    @FXML
    private void handleSaveButtonAction() {
        Template selectedTemplate = templateListView.getSelectionModel().getSelectedItem();

        if (selectedTemplate != null && selectedExercise != null) {
            try {
                ExercisePosition exercisePosition = new ExercisePosition(selectedExercise.getId(), selectedTemplate.getId());
                exercisePositionRepository.insert(exercisePosition);

                showSuccessMessage("Vorlage gespeichert", "Die Übung wurde erfolgreich zur Vorlage hinzugefügt.");
            } catch (Exception e) {
                showErrorMessage("Fehler beim Speichern der Vorlage", e.getMessage());
            }
        } else {
            showErrorMessage("Keine Vorlage oder Übung ausgewählt", "Bitte wählen Sie eine Vorlage und eine Übung aus, um sie zu speichern.");
        }
    }

    private void showSuccessMessage(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
