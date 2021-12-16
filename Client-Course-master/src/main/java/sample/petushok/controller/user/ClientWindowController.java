package sample.petushok.controller.user;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.petushok.alerts.AlertWindows;
import sample.petushok.model.Project;
import sample.petushok.model.User;
import sample.petushok.windows.WindowsCreator;
import sample.petushok.service.Client;

public class ClientWindowController {

    @FXML
    public Button сonnectButton;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addProjectButton;

//    @FXML
//    private Button connectButton;

    @FXML
    private Button signOutButton;

    @FXML
    private Button updateProjectButton;

    @FXML
    private TableView<Project> tableViewProject;

    @FXML
    private TableColumn<Project, String> nameProjectTab;

    @FXML
    private TableColumn<Project, Double> costTab;

    @FXML
    private TableColumn<Project, String> timeTab;

    @FXML
    private TableColumn<Project, String> aboutTab;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button createDocButton;

    @FXML
    private Button deleteProjectButton;

    @FXML
    private Button profileButton;


    private final ObservableList<Project> listProject = FXCollections.observableArrayList();


    @FXML
    void initialize() {


        profileButton.setOnAction(event -> {

            WindowsCreator.windowsCreator.addNewWindow("/fxml/editAccount.fxml",profileButton);
        });

        сonnectButton.setOnAction(event ->{
            WindowsCreator.windowsCreator.addNewWindow("/fxml/connect.fxml",сonnectButton);
        });

        searchButton.setOnAction(event -> {

            try {
                initTab(searchDataProject());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        try {
            initTab(findDataProject());
            initField();
        } catch (IOException e) {
            e.printStackTrace();
        }


        createDocButton.setOnAction(event -> {
            try {
                createFileToClient();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        addProjectButton.setOnAction(event -> {
           WindowsCreator.windowsCreator.addNewWindow("/fxml/windowToAddProject.fxml", addProjectButton);
        });

        signOutButton.setOnAction(event -> {
            signOut();
            WindowsCreator.windowsCreator.addNewWindow("/fxml/login.fxml", addProjectButton);
        });

        updateProjectButton.setOnAction(event -> {

            try {
                initTab(findDataProject());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteProjectButton.setOnAction(event -> {
            deleteProject();
        });
    }

    @FXML
    private  void deleteProject(){
        if(tableViewProject.getSelectionModel().getSelectedItem() != null) {
            int  count = tableViewProject.getSelectionModel().getSelectedCells().get(0).getRow();

                Client.interactionsWithServer.sendMsg("deleteProjectByID");
                Client.interactionsWithServer.sendMsg(String.valueOf(listProject.get(count).getId()));
                try {
                    initTab(findDataProject());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AlertWindows.alertWindows.alertWindowShowWarning("Операция прошла успешно !");



        }
        else {
            AlertWindows.alertWindows.alertWindowShowWarning("Вы не выбрали строку !");

        }
    }

    private void createFileToClient() throws IOException {


        if(listProject.size()>0) {

            FileWriter csvWriter = new FileWriter(User.currentUser.getEmail() + ".doc");
            for (Project rowData : listProject) {
                csvWriter.append(rowData.toString());
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
            AlertWindows.alertWindows.alertWindowShowWarning("Документ был создан!");

        }else{
            AlertWindows.alertWindows.alertWindowShowWarning("У вас нету проектов!");

        }
    }

    private void initTab(LinkedList<Project> listDb) {
        listProject.clear();
        listProject.addAll(listDb);
        nameProjectTab.setCellValueFactory(new PropertyValueFactory<Project, String>("nameProject"));
        costTab.setCellValueFactory(new PropertyValueFactory<Project, Double>("cost"));
        timeTab.setCellValueFactory(new PropertyValueFactory<Project, String>("time"));
        aboutTab.setCellValueFactory(new PropertyValueFactory<Project, String>("about"));


        searchField.setText("");// Для поиска пустого значения

        tableViewProject.setItems(listProject);
    }

    private LinkedList<Project> findDataProject() throws IOException {
        Client.interactionsWithServer.sendMsg("showProjectById");
        Client.interactionsWithServer.sendMsg(String.valueOf(User.currentUser.getIdUser()));

         return Client.interactionsWithServer.showProject();
    }
    private LinkedList<Project> searchDataProject() throws IOException {

        if(!searchField.getText().equals("")) {
            Client.interactionsWithServer.sendMsg("searchProjectByNameProject");
            Client.interactionsWithServer.sendMsg(searchField.getText()+" "+User.currentUser.getIdUser());

        return Client.interactionsWithServer.showProject();
        }
        else{
            AlertWindows.alertWindows.alertWindowShowWarning("Введите название пректа в графу поиска!");

            return findDataProject();
        }
    }
    private void signOut(){
        Client.interactionsWithServer.sendMsg("signOut");

    }

    private String calculate () throws IOException {
        int count=-1;

            if(tableViewProject.getSelectionModel().getSelectedItem() != null) {
                LinkedList<Project> projectInTable =getListInTableView();
                count = tableViewProject.getSelectionModel().getSelectedCells().get(0).getRow();

              Client.interactionsWithServer.sendMsg("searchTotal");
              Client.interactionsWithServer.sendMsg(String.valueOf(projectInTable.get(count).getId()));

            }
            else {
                AlertWindows.alertWindows.alertWindowShowWarning("Вы не выбрали строку !");

                return "";
            }

            return Client.interactionsWithServer.getTotalProject();
    }

    private LinkedList<Project> getListInTableView(){
        LinkedList<Project> list =  new LinkedList<>();
        list.addAll(listProject);
        return list;

    }
    private void initField() throws IOException {

        Client.interactionsWithServer.sendMsg("currentUserData");
        Client.interactionsWithServer.sendMsg(String.valueOf(User.currentUser.getIdUser()));
        Client.interactionsWithServer.initUserFullParams();


    }
}
