package com.example.stayfit.controller;

import com.example.stayfit.model.entity.User;
import com.example.stayfit.model.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class loginController extends stayfitController {

    public TextField userNameField;
    public PasswordField passwordField;
    public Label failedLoginLabel;

    public void onBtnLogin(ActionEvent actionEvent) throws IOException {
        try{
            if(userNameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                passwordField.setText("");
                userNameField.setText("");
                throw new Exception();
            }

            UserRepository userRepository = new UserRepository();
            List<User> users = userRepository.findAll();
            List<User> filteredUsers = users.stream().filter(u -> u.getName().equals(userNameField.getText())).collect(Collectors.toList());

            if(filteredUsers.isEmpty()){
                userRepository.insert(new User(userNameField.getText(),
                        passwordField.getText()));
            }
            else {
                if(filteredUsers.size() != 1){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();
                    passwordField.setText("");
                    userNameField.setText("");
                    return;
                }

                User user = filteredUsers.get(0);

                if(!user.getPassword().equals(passwordField.getText())){
                    failedLoginLabel.setText("wrong password");
                    passwordField.setText("");
                    return;
                }
            }

            failedLoginLabel.setText("");
            getNewStage("template");
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login failed");
            alert.setContentText("Incorrect Username and/or Password");
            alert.showAndWait();
            passwordField.setText("");
            userNameField.setText("");
        }
    }
}
