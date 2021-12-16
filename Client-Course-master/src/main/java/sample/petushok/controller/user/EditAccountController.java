package sample.petushok.controller.user;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.petushok.controller.constants.ConstParams;
import sample.petushok.model.User;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;

public class EditAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editProfileButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

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
            WindowsCreator.windowsCreator.addNewWindow("/fxml/clientWindow.fxml",returnSignInButton);

        });

        editProfileButton.setOnAction(event -> {
            sendDataCurrentUser();


        });

    }
    private void sendDataCurrentUser(){

        if(valid()) {
            Client.interactionsWithServer.sendMsg("updateUserData");
            Client.interactionsWithServer.sendMsg(User.currentUser.getIdUser() + " " + nameField.getText() + " " + lastNameField.getText() + " " + emailField.getText() + " " + passwordField.getText());
            showAlert("Аккаунт изменен !");
            WindowsCreator.windowsCreator.addNewWindow("/fxml/clientWindow.fxml",returnSignInButton);

        }else {
            showAlert("Аккаунт не  изменен !");
          }
        }
    private void initField()  {
        emailField.setText(User.currentUser.getEmail());
        nameField.setText(User.currentUser.getName());
        lastNameField.setText(User.currentUser.getLastName());
        passwordField.setText(User.currentUser.getPassword());

    }
    private boolean valid () {

        if(nameField.getText().equals("") || lastNameField.getText().equals("")  || emailField.getText().equals("")  || passwordField.getText().equals(""))
        {
            return false;
        }

        Pattern pattern = Pattern.compile(ConstParams.EMAIL_PATTERN);
        Matcher  matcher = pattern.matcher(emailField.getText());

        if(!matcher.matches() || !(passwordField.getText().length() >3)){
          return false;
        }
        User.currentUser.setEmail(emailField.getText());
        return true;
    }


    private void showAlert(String name) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error ...");
        alert.setHeaderText(null);
        alert.setContentText(name);
        alert.showAndWait();
    }
}
