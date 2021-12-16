package service;

import database.DataBaseHandler;
import repository.ServiceProjectApp;
import server.ServerConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class ServiceProjectAppImplementation implements ServiceProjectApp {
    private final DataBaseHandler dbHandler;
    private final BufferedReader in;
    private final int currentCountClient;

    public ServiceProjectAppImplementation(DataBaseHandler dbHandler, BufferedReader in, int currentCountClient) {
        this.dbHandler = dbHandler;
        this.in = in;
        this.currentCountClient = currentCountClient;
    }

    @Override
    public void addProjectInDoneDataBase(){
        try {
            dbHandler.addDoneProject(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void showAllProjectsInDone(){
        LinkedList<String> listDb = new LinkedList<>();
        LinkedList<String> countProject = dbHandler.showHistory();
        int count;
        for(int i=0;i<countProject.size();i++){
            count = Integer.parseInt(countProject.get(i));
            dbHandler.showProjectByIdDone(listDb,count);

        }
        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(listDb.size()));
        for(int i=0;i<listDb.size();i++){
            System.out.println(listDb.get(i));
            ServerConnect.serverList.get(currentCountClient).send(listDb.get(i));
        }

    }
    @Override
    public void searchTotal(){
        try {

            ServerConnect.serverList.get(currentCountClient).send(  dbHandler.totalProject(Integer.parseInt(in.readLine())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void redactionUser() {
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            dbHandler.redactionUser(Integer.parseInt(subStr[0]),subStr[1],subStr[2], Integer.parseInt(subStr[3]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void showAllUsers(){
        LinkedList<String> list = dbHandler.showAllUsers();
        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(list.size()));
        for(String s:list){

            ServerConnect.serverList.get(currentCountClient).send(s);

        }
    }
    @Override
    public void showAllProjects(){
        LinkedList<String> list = dbHandler.showAllProjects();
        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(list.size()));
        for(String s:list){

            ServerConnect.serverList.get(currentCountClient).send(s);

        }
    }
    @Override
    public void updateUser(){
        String[] str;

        try {
            str=in.readLine().split(" ");
            dbHandler.updateUser(Integer.parseInt(str[0]),str[1],str[2],str[3],str[4]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statusUser(){
        String[] subStr;

        try {

            subStr = in.readLine().split(" ");
            dbHandler.statusUser(Integer.parseInt(subStr[0]),subStr[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFullParamsUser(){
        try {

            ServerConnect.serverList.get(currentCountClient).send(dbHandler.currentUserData(Integer.parseInt(in.readLine())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteProjectById(){
        try {
            dbHandler.deleteProjectByName(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void searchProject() throws IOException {

        String[] str;
        str=in.readLine().split(" ");


        LinkedList<String> list = dbHandler.showProjectByName(str[0],Integer.parseInt(str[1]));
        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(list.size()));
        for(String s:list){

            ServerConnect.serverList.get(currentCountClient).send(s);

        }
    }
    @Override
    public void showAllWorkers(){
        LinkedList<String> list = dbHandler.showAllWorkers();
        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(list.size()));
        for(String s:list){

            ServerConnect.serverList.get(currentCountClient).send(s);

        }
    }
    @Override
    public void showProjectById() throws IOException {
        LinkedList<String> list = dbHandler.showProjectsById(Integer.parseInt(in.readLine()));
        ServerConnect.serverList.get(currentCountClient).send(String.valueOf(list.size()));
        for(String s:list){

            ServerConnect.serverList.get(currentCountClient).send(s);

        }

    }

    @Override
    public void addInDatabaseProject(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            if(subStr.length>3 &&
                    validateInput(subStr[1],subStr[2],subStr[3],subStr[4])) {
                if (dbHandler.addProjectInDb(Integer.parseInt(subStr[0]), subStr[1], Double.parseDouble(subStr[2]), subStr[3], subStr[4])) {
                    ServerConnect.serverList.get(currentCountClient).send("1");
                } else {
                    ServerConnect.serverList.get(currentCountClient).send("0");
                }
            }
            else{
                ServerConnect.serverList.get(currentCountClient).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectInDb(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            if(subStr.length>3 &&
                    validateInput(subStr[1],subStr[2],subStr[3],subStr[4])) {
                if (dbHandler.connectInDb(Integer.parseInt(subStr[0]), subStr[1], Double.parseDouble(subStr[2]), subStr[3], subStr[4])) {
                    ServerConnect.serverList.get(currentCountClient).send("1");
                } else {
                    ServerConnect.serverList.get(currentCountClient).send("0");
                }
            }
            else{
                ServerConnect.serverList.get(currentCountClient).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean validateInput(String project,String cost,String time,String about){

        if(!project.equals("") || !cost.equals("") || !time.equals("") || !about.equals("")) {

            try {
                Double.parseDouble(cost);
            } catch (NumberFormatException | NullPointerException nfe) {
                return false;
            }

        }else {
            return false;
        }


        return true;
    }
    @Override
    public void registrationUser(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            if(dbHandler.addUserInDb(subStr[0], subStr[1],"0",subStr[2],subStr[3])){
                ServerConnect.serverList.get(currentCountClient).send("1");
            }
            else{
                ServerConnect.serverList.get(currentCountClient).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void findUserInDataBase(){
        try {
            String[] subStr = in.readLine().split(" ");
            String check= dbHandler.authUser(subStr[0], subStr[1]);
            if (!check.equals("false")) {
                ServerConnect.serverList.get(currentCountClient).send("1");
                ServerConnect.serverList.get(currentCountClient).send(check);
            } else {
                ServerConnect.serverList.get(currentCountClient).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
