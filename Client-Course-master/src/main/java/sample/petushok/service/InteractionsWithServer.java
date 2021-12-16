package sample.petushok.service;

import javafx.scene.control.Button;
import sample.petushok.alerts.AlertWindows;
import sample.petushok.file.append.FileProject;
import sample.petushok.model.Project;
import sample.petushok.model.User;
import sample.petushok.valid.Validation;
import sample.petushok.windows.WindowsCreator;


import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class InteractionsWithServer {
    private  BufferedReader in;
    private  BufferedWriter out;

    public InteractionsWithServer(Socket clientSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void authorization(String login, String password, Button button){
        if(Validation.validation.validEmailAndPassword(login,password)) {
            sendMsg("auth");// Чтобы сервер понимал что надо сделать
            sendMsg(login + " " + password);
            if (checkUserInDataBase()) {
                getUserIdAndRoll(login);
                if(User.currentUser.getRoll()==0) {
                    WindowsCreator.windowsCreator.addNewWindow("/fxml/clientWindow.fxml", button);
                }
                if(User.currentUser.getRoll()==1){
                    WindowsCreator.windowsCreator.addNewWindow("/fxml/adminWindow.fxml", button);
                }
            } else {
                AlertWindows.alertWindows.alertWindowShowDanger("Неверный логин или пароль !");
            }
        }else{
            AlertWindows.alertWindows.alertWindowShowDanger("Неверный email формат !");

        }
    }

   public void registrationUser(String email,String password,String name,String lastName,Button signUpButton){

       if(!name.isEmpty() &&!lastName.isEmpty() && !email.isEmpty() && !password.isEmpty() && Validation.validation.validEmailAndPasswordFromRegistration(email,password)) {
           sendMsg("registration");
           sendMsg(email + " " + password+ " " +name+" "+ lastName);
           if(!checkUserInDataBase()){
               AlertWindows.alertWindows.alertWindowShowDanger("Аккаунт не был создан !");
               return;
           }
           WindowsCreator.windowsCreator.addNewWindow("/fxml/login.fxml",signUpButton);

           AlertWindows.alertWindows.alertWindowShowWarning("Аккаунт  был создан !");
       }
       else{
           AlertWindows.alertWindows.alertWindowShowDanger("Ячейки пусты ! " +
                   "Или неверный email формат ");

       }
   }



    public void sendMsg(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUserIdAndRoll(String email){
        try {

            String[] subStr = in.readLine().split(" ");
            User.currentUser= new User(email,Integer.parseInt(subStr[0]),Integer.parseInt(subStr[1]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserInDataBase() {
        try {
            if(in.readLine().equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkAddProjectInDb(){
        try {
            if(in.readLine().equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public LinkedList<User> showWorker() throws IOException {
        LinkedList<User> listWorker = new LinkedList<>();
        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringInWorker(in.readLine(),listWorker);
        }
        return listWorker;
    }


    public LinkedList<Project> showProject() throws IOException {
        LinkedList<Project> listProject = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringInProject(in.readLine(), listProject);
        }
        return listProject;
    }
    private void parseStringInProject(String str, LinkedList<Project> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Project(Integer.parseInt(subStr[0]),subStr[1],Double.parseDouble(subStr[2]),subStr[3],subStr[4]));
    }

    private void parseStringInWorker(String str,LinkedList<User> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add( new User(subStr[0],subStr[1],subStr[2],subStr[3],Integer.parseInt(subStr[4]),Integer.parseInt(subStr[5]),subStr[6]));
    }

    public void initUserFullParams() throws IOException {
        String[] subStr;

        subStr = in.readLine().split(" ");
        User.currentUser.setName(subStr[0]);
        User.currentUser.setLastName(subStr[1]);
        User.currentUser.setPassword(subStr[2]);


    }

    public LinkedList<Project> showAllProject() throws IOException {
        LinkedList<Project> listProject = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringInProjectAll(in.readLine(), listProject);
        }
        return listProject;
    }

    private void parseStringInProjectAll(String str, LinkedList<Project> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Project(Integer.parseInt(subStr[0]),subStr[1],Double.parseDouble(subStr[2]), subStr[3], subStr[4]));
    }


    public LinkedList<User> showAllUsers() throws IOException {
        LinkedList<User> lisUsers = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringUsers(in.readLine(),lisUsers);
        }
        return lisUsers;
    }

    private void parseStringUsers(String readLine, LinkedList<User> lisUsers) {
        String[] subStr;
        subStr = readLine.split(" ");
        lisUsers.add(new User(Integer.parseInt(subStr[0]),subStr[1],subStr[2],Integer.parseInt(subStr[3])));
    }

    public String getTotalProject() throws IOException {
        return  in.readLine();
    }

    public void getHistory() throws IOException {
        int count = Integer.parseInt(in.readLine());

        FileProject.fileProject.createFile();
        for(int i=0;i<count;i++){
            FileProject.fileProject.addDataInFile(in.readLine(),count);
        }

    }
}
