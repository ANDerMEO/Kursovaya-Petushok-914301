package sample.petushok.controller.user;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.petushok.alerts.AlertWindows;
import sample.petushok.model.User;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;

public class AddNewProjectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameProjectField;

    @FXML
    private TextField costField;

    @FXML
    private TextField timeField;

    @FXML
    private TextArea aboutField;

    @FXML
    private Button addNewProjectButton;

    @FXML
    private Button returnButton;



    @FXML
    void initialize() {
        initField();
    addNewProjectButton.setOnAction(event -> {
        addNewProjectInDb();
    });

    returnButton.setOnAction(event -> {
        WindowsCreator.windowsCreator.addNewWindow("/fxml/clientWindow.fxml",returnButton);
    });

    }
    private void addNewProjectInDb(){


        Client.interactionsWithServer.sendMsg("addProject");
        Client.interactionsWithServer.sendMsg(User.currentUser.getIdUser()+" "+ nameProjectField.getText()+" "+
                        costField.getText()+" "+ timeField.getText() +" "+ aboutField.getText());
        if(Client.interactionsWithServer.checkAddProjectInDb()){

            AlertWindows.alertWindows.alertWindowShowWarning("Проект был добавлен !");

        }
        else {
            AlertWindows.alertWindows.alertWindowShowWarning("Вы где-то ошиблись в этой жизни!");

        }}



    private void initField(){
        nameProjectField.setText("");
        costField.setText("");
        timeField.setText("");
        aboutField.setText("");

    }

}