package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Exercise;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.repository.ExerciseRepository;
import com.example.stayfit.model.repository.TemplateRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
import static com.example.stayfit.controller.ExerciseListController.selectedTemplate;

public class templateController extends stayfitController {
    @FXML
    public ListView templateLv;
    public TextField searchField;
    public TextField nameField;
    private TemplateRepository templateRepository;
    private FilteredList<Template> templates;

    @FXML
    private void initialize() throws SQLException {
        this.templateRepository = new TemplateRepository(currentUser);

        this.templates = new FilteredList<>(this.templateRepository.getTemplates());
        this.templateLv.setItems(this.templates);

        this.templateRepository.getTemplates().addListener(new ListChangeListener<Template>() {
            @Override
            public void onChanged(Change<? extends Template> change) {
                System.out.println("Something changed!");
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Template template : change.getAddedSubList()) {
                            System.out.printf("%s was added.%n", template);
                        }
                    }
                    if (change.wasRemoved()) {
                        for (Template template : change.getRemoved()) {
                            System.out.printf("%s was removed.%n", template);
                        }
                    }
                }
            }
        });

        this.templateLv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Template>() {
            @Override
            public void changed(ObservableValue<? extends Template> observableValue, Template template1, Template t) {
                Template template = (Template) observableValue.getValue();

                if(template != null) {
                    nameField.setText(template.getName() + "");
                }
                else {
                    nameField.setText("");
                }
            }
        });
    }

    public void onBtnAdd(ActionEvent actionEvent) {
        try{
            if(nameField.getText().isEmpty()){
                throw new Exception();
            }
            Template exercise = new Template(currentUser ,nameField.getText());

            this.templateRepository.save(exercise);
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error", "Ungültiger Name");
        }
    }

    public void onBtnSearch(ActionEvent actionEvent) {
        String search = searchField.getText();

        this.templates.setPredicate(c -> c.getName().toLowerCase().contains(search.toLowerCase()));
    }

    public void onBtnSave(ActionEvent actionEvent) {
        try{
            Template template = (Template) templateLv.getSelectionModel().getSelectedItem();
            template.setName(nameField.getText());

            if(template == null)
                throw new Exception();

            this.templateRepository.save(template);
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error",
                    "Es wurde keine Übung ausgewählt");
        }
    }

    public void onBtnDelete(ActionEvent actionEvent) {
        try {
            this.templateRepository.delete((Template) templateLv.getSelectionModel().getSelectedItem());
        }
        catch (Exception e){
            createAlertAndShow(Alert.AlertType.ERROR, "Error", "Übung konnte nicht gelöscht werden");
        }
    }

    public void onListViewKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            templateLv.getSelectionModel().clearSelection();
            nameField.setText("");
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onBtnSearch(null);
        }
    }

    @FXML
    private void onBtnDetails() {
        try {
            selectedTemplate = (Template) templateLv.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stayfit/view/exerciseList.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Exercises List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            createAlertAndShow(Alert.AlertType.ERROR, "Error",
                    "Es wurde keine Vorlage ausgewählt");
        }
    }

}
