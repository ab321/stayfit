package com.example.stayfit.controller;

import com.example.stayfit.model.entity.Set;
import com.example.stayfit.model.entity.Template;
import com.example.stayfit.model.repository.SetRepository;
import com.example.stayfit.model.repository.TemplateRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;

public class setController extends stayfitController {
    public TextField repField;
    public TextField weightField;
    public TextField templateField;
    public ListView setLv;
    public DatePicker datePicker;
    private SetRepository setRepository = new SetRepository(currentUser);;
    private FilteredList<Set> sets;


    @FXML
    private void initialize() throws SQLException {
        this.setRepository.getSets().addListener(new ListChangeListener<Set>() {
            @Override
            public void onChanged(Change<? extends Set> change) {
                System.out.println("Something changed!");
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Set set : change.getAddedSubList()) {
                            System.out.printf("%s was added.%n", set);
                        }
                    }
                    if (change.wasRemoved()) {
                        for (Set set : change.getRemoved()) {
                            System.out.printf("%s was removed.%n", set);
                        }
                    }
                }
            }
        });

        this.setLv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Set>() {
            @Override
            public void changed(ObservableValue<? extends Set> observableValue, Set set1, Set s) {
                Set set = (Set) observableValue.getValue();

                if(set != null){
                    templateField.setText(set.getTemplate().getName());
                    repField.setText(set.getReps() + "");
                    weightField.setText(set.getWeight() + " kg");
                }
                else{
                    templateField.setText("");
                    repField.setText("");
                    weightField.setText("");
                }
            }
        });
    }

    public void onListViewKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            setLv.getSelectionModel().clearSelection();
            templateField.setText("");
            repField.setText("");
            weightField.setText("");
        }
    }

    public void onActionDatePicker(ActionEvent actionEvent) throws SQLException {
        this.sets = new FilteredList<>(this.setRepository.getSets());

        this.sets.setPredicate(set -> set.getDate().toString().equals(datePicker.getValue().toString()));
        this.setLv.setItems(this.sets);
    }
}
