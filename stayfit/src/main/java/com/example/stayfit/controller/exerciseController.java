package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.repository.ExerciseRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class exerciseController extends stayfitController {
    public ListView exerciseLv;
    public TextField searchField;
    public TextField nameField;
    private ExerciseRepository exerciseRepository;
    private FilteredList<Exercise> exercises;

    @FXML
    private void initialize() throws SQLException {
        this.exerciseRepository = new ExerciseRepository();

        this.exercises = new FilteredList<>(this.exerciseRepository.getExercises());
        this.exerciseLv.setItems(this.exercises);

        this.exerciseRepository.getExercises().addListener(new ListChangeListener<Exercise>() {
            @Override
            public void onChanged(Change<? extends Exercise> change) {
                System.out.println("Something changed!");
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Exercise exercise : change.getAddedSubList()) {
                            System.out.printf("%s was added.%n", exercise);
                        }
                    }
                    if (change.wasRemoved()) {
                        for (Exercise exercise : change.getRemoved()) {
                            System.out.printf("%s was removed.%n", exercise);
                        }
                    }
                }
            }
        });

        this.exerciseLv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exercise>() {
            @Override
            public void changed(ObservableValue<? extends Exercise> observableValue, Exercise exercise1, Exercise e) {
                Exercise exercise = (Exercise) observableValue.getValue();
                nameField.setText(exercise.getName() + "");

            }
        });
    }

    public void onBtnAdd(ActionEvent actionEvent) {
        try{
            if(!nameField.getText().isEmpty()){
                throw new Exception();
            }
            Exercise exercise = new Exercise(nameField.getText());

            this.exerciseRepository.save(exercise);
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error", "Ungültiger Name");
        }
    }

    public void onBtnSearch(ActionEvent actionEvent) {
        String search = searchField.getText();

        this.exercises.setPredicate(c -> c.getName().toLowerCase().contains(search.toLowerCase()));
    }

    public void onBtnSave(ActionEvent actionEvent) {
    }

    public void onBtnDelete(ActionEvent actionEvent) {
        try {
            this.exerciseRepository.delete((Exercise) exerciseLv.getSelectionModel().getSelectedItem());
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error", "Übung konnte nicht gelöscht werden");
        }
    }
}
