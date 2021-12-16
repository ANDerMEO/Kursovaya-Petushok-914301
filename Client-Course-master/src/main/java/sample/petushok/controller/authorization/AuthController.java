package sample.petushok.controller.authorization;


import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;


public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField passwordInput;

    @FXML
    private TextField loginInput;

    @FXML
    private Button signInButton;

    @FXML
    private Button registrationButton;



    @FXML
    void initialize() {
        initField();

        signInButton.setOnAction(actionEvent -> {
            Client.interactionsWithServer.authorization(loginInput.getText(),passwordInput.getText(),signInButton);
        });

        registrationButton.setOnAction(actionEvent ->{
            WindowsCreator.windowsCreator.addNewWindow("/fxml/registration.fxml",registrationButton);
        });

    }

    private void initField(){
        loginInput.setText("");
        passwordInput.setText("");
    }

}
