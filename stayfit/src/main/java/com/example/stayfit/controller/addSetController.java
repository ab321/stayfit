package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Set;
import com.example.stayfit.model.repository.ExercisePositionRepository;
import com.example.stayfit.model.repository.SetRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class addSetController extends stayfitController {
    public TextField exerciseField;
    public TextField templateField;
    public Spinner repSpinner;
    public TextField weightField;
    private Set currentSet = getCurrentSet();

    @FXML
    private void initialize() {
        exerciseField.setText(currentSet.getExercise().getName());
        templateField.setText(currentSet.getTemplate().getName());
    }

    public void onBtnAdd(ActionEvent actionEvent) {
        try {
            if(weightField.getText().isEmpty()) {
                throw new Exception();
            }
            double weight = Double.parseDouble(weightField.getText());
            int reps = (int) repSpinner.getValue();
            currentSet.setReps(reps);
            currentSet.setWeight(weight);

            SetRepository setRepository = new SetRepository(currentUser);
            setRepository.insert(currentSet);

            weightField.getScene().getWindow().hide();
        }
        catch (Exception e) {
            createAlertAndShow(Alert.AlertType.WARNING, "Warnung", "Ungültige Eingabe");
            weightField.setText("");
        }
    }
}
