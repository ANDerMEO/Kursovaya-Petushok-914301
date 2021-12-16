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
import javafx.scene.control.cell.PropertyValueFactory;
import sample.petushok.model.User;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;

public class AllWorkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> workersTable;

    @FXML
    private TableColumn<User, String> nameTab;

    @FXML
    private TableColumn<User, String> lastNameTable;

    @FXML
    private TableColumn<User, String> emailTab;

    @FXML
    private TableColumn<User, String> passwordTab;

    @FXML
    private TableColumn<User, Integer> rollTab;

    @FXML
    private TableColumn<User, String> positionTab;

    @FXML
    private Button returnButton;

    private final ObservableList<User> listWorkers = FXCollections.observableArrayList();


    @FXML
    void initialize() {

        try {
            initTable(findWorker());
        } catch (IOException e) {
            e.printStackTrace();
        }

        returnButton.setOnAction(event -> {
            WindowsCreator.windowsCreator.addNewWindow("/fxml/adminWindow.fxml", returnButton);
        });

    }

    private LinkedList<User> findWorker() throws IOException {
        Client.interactionsWithServer.sendMsg("showWorker");

        return Client.interactionsWithServer.showWorker();
    }

    private void initTable(LinkedList<User> listDb){

        listWorkers.clear();
        listWorkers.addAll(listDb);
        nameTab.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        lastNameTable.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        emailTab.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        passwordTab.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        rollTab.setCellValueFactory(new PropertyValueFactory<User, Integer>("roll"));
        positionTab.setCellValueFactory(new PropertyValueFactory<User, String>("position"));



        workersTable.setItems(listWorkers);
    }



}
