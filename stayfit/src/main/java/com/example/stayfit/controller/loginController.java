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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class loginController extends stayfitController {

    public TextField userNameField;
    public PasswordField passwordField;
    public Label failedLoginLabel;

    public void onBtnLogin(ActionEvent actionEvent) throws IOException {
        try{
            if(userNameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                throw new Exception();
            }

            UserRepository userRepository = new UserRepository();
            List<User> users = userRepository.findAll();
            List<User> filteredUsers = getFilteredUsers(users, user -> user.getName().equals(userNameField.getText()));

            if(filteredUsers.isEmpty()){
                createAlertAndShow(Alert.AlertType.WARNING, "Account not found",
                        "Seems like you dont have an account\nRegister first");
                userNameField.setText("");
                passwordField.setText("");
                return;
            }
            else {
                if(filteredUsers.size() != 1){
                    createAlertAndShow(Alert.AlertType.ERROR, "Error",
                            "Something went wrong");
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
            createAlertAndShow(Alert.AlertType.WARNING, "Login failed",
                    "Incorrect Username and/or Password");
            passwordField.setText("");
            userNameField.setText("");
        }
    }

    private void createAlertAndShow(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private List<User> getFilteredUsers(List<User> users, Predicate<User> predicate){
        return users.stream().filter(predicate).collect(Collectors.toList());
    }

    public void onBtnRegister(ActionEvent actionEvent) {
        try{
            if(userNameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                passwordField.setText("");
                userNameField.setText("");
                createAlertAndShow(Alert.AlertType.WARNING, "Login failed",
                        "Incorrect Username and/or Password");
                return;
            }

            UserRepository userRepository = new UserRepository();
            List<User> users = userRepository.findAll();
            List<User> filteredUsers = getFilteredUsers(users, user -> user.getName().equals(userNameField.getText()));

            if(!filteredUsers.isEmpty()){
                createAlertAndShow(Alert.AlertType.WARNING, "Username given",
                        "Username is already given\nLook for new Username");
                return;
            }

            userRepository.insert(new User(userNameField.getText(),
                    passwordField.getText()));

            failedLoginLabel.setText("");
            getNewStage("template");
        }
        catch (Exception ex){
            createAlertAndShow(Alert.AlertType.ERROR, "Error",
                    "Something went wrong");
        }
        finally {
            passwordField.setText("");
            userNameField.setText("");
        }
    }
}
