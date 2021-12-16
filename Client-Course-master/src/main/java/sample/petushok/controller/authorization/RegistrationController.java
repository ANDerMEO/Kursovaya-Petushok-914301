package sample.petushok.controller.authorization;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button returnSignInButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nameField;

    @FXML
    void initialize() {
        initField();
        returnSignInButton.setOnAction(event -> {
            WindowsCreator.windowsCreator.addNewWindow("/fxml/login.fxml",returnSignInButton);
        });

        signUpButton.setOnAction(event -> {

            String email = emailField.getText();
            String password = passwordField.getText();
            String lastName =  lastNameField.getText();
            String name = nameField.getText();

            Client.interactionsWithServer.registrationUser(email,password,name,lastName,signUpButton);

        });


    }



    private void initField(){
        emailField.setText("");
        passwordField.setText("");
        lastNameField.setText("");
        nameField.setText("");

    }
}
