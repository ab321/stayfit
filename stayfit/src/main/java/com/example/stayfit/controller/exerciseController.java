package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.repository.ExerciseRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.stayfit.controller.TemplateListController.selectedExercise;

public class exerciseController extends stayfitController {
    @FXML
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
            if(nameField.getText().isEmpty()){
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
        try{
            Exercise exercise = (Exercise) exerciseLv.getSelectionModel().getSelectedItem();
            exercise.setName(nameField.getText());

            if(exercise == null)
                throw new Exception();

            this.exerciseRepository.save(exercise);
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error",
                    "Es wurde keine Übung ausgewählt");
        }
    }

    public void onBtnDelete(ActionEvent actionEvent) {
        try {
            this.exerciseRepository.delete((Exercise) exerciseLv.getSelectionModel().getSelectedItem());
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error", "Übung konnte nicht gelöscht werden");
        }
    }

    public void onListViewKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            exerciseLv.getSelectionModel().clearSelection();
            nameField.setText("");
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onBtnSearch(null);
        }
    }

    public void onAddToTemplate(ActionEvent actionEvent) {
        try {
            selectedExercise = (Exercise) exerciseLv.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stayfit/view/templateList.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Template List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
