package sample.petushok.controller.user;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.petushok.alerts.AlertWindows;
import sample.petushok.model.Project;
import sample.petushok.model.User;
import sample.petushok.service.Client;
import sample.petushok.windows.WindowsCreator;

public class ConnectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button connectButton;

    @FXML
    private TableColumn<Project, Integer> idProjectTab;

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


    private final ObservableList<Project> listProject = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        try {
            initTab(findDataProject());
//            initField();
        } catch (IOException e) {
            e.printStackTrace();
        }
        backButton.setOnAction(event -> {

            WindowsCreator.windowsCreator.addNewWindow("/fxml/clientWindow.fxml",backButton);
        });
        connectButton.setOnAction(event -> {
            addNewProjectInDb();

        });

    }
    @FXML
    private void initTab(LinkedList<Project> listDb) {
        listProject.clear();
        listProject.addAll(listDb);

        idProjectTab.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        nameProjectTab.setCellValueFactory(new PropertyValueFactory<Project, String>("nameProject"));
        costTab.setCellValueFactory(new PropertyValueFactory<Project, Double>("cost"));
        timeTab.setCellValueFactory(new PropertyValueFactory<Project, String>("time"));
        aboutTab.setCellValueFactory(new PropertyValueFactory<Project, String>("about"));


//        searchField.setText("");// Для поиска пустого значения

        tableViewProject.setItems(listProject);
    }
    private LinkedList<Project> findDataProject() throws IOException {
        Client.interactionsWithServer.sendMsg("showAllProjects");
        return Client.interactionsWithServer.showAllProject();

    }
    private void addNewProjectInDb(){
        int  count = tableViewProject.getSelectionModel().getSelectedCells().get(0).getRow();
        Client.interactionsWithServer.sendMsg("connectInDb");
        Client.interactionsWithServer.sendMsg(User.currentUser.getIdUser() + " " +  listProject.get(count).getNameProject() + " "
                + Double.valueOf(listProject.get(count).getCost()) + " " + listProject.get(count).getTime() + " "
                + listProject.get(count).getAbout());
//        Client.interactionsWithServer.sendMsg(User.currentUser.getIdUser()+" "+ nameProjectTab.getSe()+" "+
//                costTab.getText()+" "+ timeTab.getText()+" "+aboutTab.getText());
        if(Client.interactionsWithServer.checkAddProjectInDb()){

            AlertWindows.alertWindows.alertWindowShowWarning("Проект был добавлен !");

        }
        else {
            AlertWindows.alertWindows.alertWindowShowWarning("Вы где-то ошиблись в этой жизни!");

        }}


}
