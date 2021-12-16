package sample.petushok.controller.admin;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.petushok.alerts.AlertWindows;
import sample.petushok.model.Project;
import sample.petushok.model.User;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;

public class AdminWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button allWorkerButton;

    @FXML
    private TableView<Project> projectTable;

    @FXML
    private TableColumn<Project, Integer> idProjectTab;

    @FXML
    private TableColumn<Project, String> nameProjectTab;

    @FXML
    private TableColumn<Project, Double> costTab;

    @FXML
    private TableColumn<Project, String> timeTab;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idUserTab;

    @FXML
    private TableColumn<User, String> emailTab;

    @FXML
    private TableColumn<User, String> passwordTab;

    @FXML
    private TableColumn<User, Integer> rollTab;

    @FXML
    private Button historyDepositButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField statusField;

    @FXML
    private Button redactionButton;

    @FXML
    private Button statusButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField rollField;

    @FXML
    private Button chooseButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button inHistoryButton;

    private final ObservableList<Project> listProject = FXCollections.observableArrayList();
    private final ObservableList<User> listUsers = FXCollections.observableArrayList();


    @FXML
    void initialize() {

        inHistoryButton.setOnAction(event -> {
            addInHistory();
            WindowsCreator.windowsCreator.addNewWindow("/fxml/adminWindow.fxml", inHistoryButton);
            });


        returnButton.setOnAction(event -> {
            WindowsCreator.windowsCreator.addNewWindow("/fxml/login.fxml",returnButton);
        });

        statusButton.setOnAction(event -> {
            Client.interactionsWithServer.sendMsg("statusUser");
            Client.interactionsWithServer.sendMsg(idField.getText()+" "+statusField.getText());
            try {
                initUsers(showAllUsers());
                idField.setText("");
                emailField.setText("");
                passwordField.setText("");
                rollField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            initProjectTable(showAllProjects());
            initUsers(showAllUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }

        allWorkerButton.setOnAction(event -> {
            WindowsCreator.windowsCreator.addNewWindow("/fxml/allWorkerTable.fxml", allWorkerButton);

        });

        chooseButton.setOnAction(event -> {
            initFieldToRedaction();
        });

        redactionButton.setOnAction(event -> {
            Client.interactionsWithServer.sendMsg("redactionUser");
            Client.interactionsWithServer.sendMsg(idField.getText()+" "+emailField.getText()+" "+passwordField.getText()+" "+rollField.getText());
            try {
                initUsers(showAllUsers());
                idField.setText("");
                emailField.setText("");
                passwordField.setText("");
                rollField.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initFieldToRedaction(){
        LinkedList<User> listUsersDb = new LinkedList<>();
        listUsersDb.addAll(listUsers);
        if(usersTable.getSelectionModel().getSelectedItem() != null) {
            int count = usersTable.getSelectionModel().getSelectedCells().get(0).getRow();
            idField.setEditable(false);
            idField.setText(String.valueOf(listUsers.get(count).getIdUser()));
            emailField.setText(listUsers.get(count).getEmail());
            passwordField.setText(listUsers.get(count).getPassword());
            rollField.setText(String.valueOf(listUsers.get(count).getRoll()));
            statusField.setText(listUsers.get(count).getPosition());
        }
        else{
            AlertWindows.alertWindows.alertWindowShowWarning("Вы не выбрали строку !");
        }


        }



    private void initProjectTable(LinkedList<Project> listDb){
        listProject.clear();
        listProject.addAll(listDb);

        idProjectTab.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        nameProjectTab.setCellValueFactory(new PropertyValueFactory<Project, String>("nameProject"));
        costTab.setCellValueFactory(new PropertyValueFactory<Project, Double>("cost"));
        timeTab.setCellValueFactory(new PropertyValueFactory<Project, String>("time"));


        projectTable.setItems(listProject);
    }

    private void initUsers(LinkedList<User> listDb){
        listUsers.clear();
        listUsers.addAll(listDb);

        idUserTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("idUser"));
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        passwordTab.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        rollTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("roll"));


        usersTable.setItems(listUsers);
    }


    private LinkedList<Project> showAllProjects() throws IOException {
        Client.interactionsWithServer.sendMsg("showAllProjects");
        return Client.interactionsWithServer.showAllProject();
    }

    private LinkedList<User> showAllUsers() throws IOException {
        Client.interactionsWithServer.sendMsg("showAllUser");
        return Client.interactionsWithServer.showAllUsers();
    }

    private void addInHistory(){
        if(projectTable.getSelectionModel().getSelectedItem() != null) {
            int count = projectTable.getSelectionModel().getSelectedCells().get(0).getRow();
            Client.interactionsWithServer.sendMsg("addInHistoryTable");
            Client.interactionsWithServer.sendMsg(String.valueOf(listProject.get(count).getId()));

//            Client.interactionsWithServer.sendMsg("showAllProjectsInHistory");


        }
        else{
            AlertWindows.alertWindows.alertWindowShowWarning("Вы не выбрали строку !");
        }

    }
}



