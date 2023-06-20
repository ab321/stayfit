package com.example.stayfit.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class calculatorController extends stayfitController {

    @FXML
    private Slider ageSlider;

    @FXML
    private Slider heightSlider;

    @FXML
    private Slider weightSlider;

    @FXML
    private Slider walkingSlider;

    @FXML
    private Slider cardioSlider;

    @FXML
    private Label ageLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label walkingLabel;

    @FXML
    private Label cardioLabel;

    @FXML
    private Label gainWeightLabel;

    @FXML
    private Label stayWeightLabel;

    @FXML
    private Label loseWeightLabel;

    @FXML
    private Label gainProteinLabel;

    @FXML
    private Label stayProteinLabel;

    @FXML
    private Label loseProteinLabel;

    @FXML
    private Label gainKohlenhydrateLabel;

    @FXML
    private Label stayKohlenhydrateLabel;

    @FXML
    private Label loseKohlenhydrateLabel;

    public void initialize() {
        ageSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ageLabel.setText("" + newValue.intValue());
            calculate();
        });

        heightSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            heightLabel.setText(newValue.intValue() + " cm");
            calculate();
        });

        weightSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            weightLabel.setText(newValue.intValue() + " kg");
            calculate();
        });

        walkingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            walkingLabel.setText(newValue.intValue() + " h/woche");
            calculate();
        });

        cardioSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            cardioLabel.setText(newValue.intValue() + " h/woche");
            calculate();
        });

        ageSlider.setValue(20);
        heightSlider.setValue(170);
        weightSlider.setValue(70);
        walkingSlider.setValue(7);
        cardioSlider.setValue(3);

    }

    public void calculate() {
        int age = (int) ageSlider.getValue();
        int height = (int) heightSlider.getValue();
        int weight = (int) weightSlider.getValue();
        int walking = (int) walkingSlider.getValue();
        int cardio = (int) cardioSlider.getValue();

        int basalMetabolicRate = 10 * weight + 6 * height - 5 * age + 5;
        int totalCalories = basalMetabolicRate + walking * 100 + cardio * 200;

        int gainWeightCalories = totalCalories + 500;
        int mainWeightCalories = totalCalories;
        int loseWeightCalories = totalCalories - 500;

        int toGainProtein = (int) (weight * 2.4);
        int toStayProtein = (weight * 2);
        int toLoseProtein = (weight * 2);

        int toGainKoh = (int) (weight * 5.5);
        int toStayKoh =  (int) (weight * 3.8);
        int toLoseKoh = weight * 2;

        gainWeightLabel.setText("Gewicht zunehmen: " + gainWeightCalories + " kcal" );
        stayWeightLabel.setText("Gewicht halten: " + mainWeightCalories + " kcal");
        loseWeightLabel.setText("Gewicht abnehmen: " + loseWeightCalories + " kcal");

        gainKohlenhydrateLabel.setText("       Kohlenhydrate: " + toGainKoh + "g");
        stayKohlenhydrateLabel.setText("             Kohlenhydrate: " + toStayKoh + "g");
        loseKohlenhydrateLabel.setText("              Kohlenhydrate: " + toLoseKoh + "g");

        gainProteinLabel.setText("         Protein: " + toGainProtein + "g");
        stayProteinLabel.setText("                     Protein: " + toStayProtein + "g");
        loseProteinLabel.setText("                          Protein: " + toLoseProtein + "g");


    }
}
