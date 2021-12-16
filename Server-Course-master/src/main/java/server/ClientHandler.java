package server;

import database.DataBaseHandler;
import repository.ServiceProjectApp;
import service.ServiceProjectAppImplementation;


import java.io.*;
import java.net.Socket;


public class ClientHandler extends Thread  {
    private final Socket socket;
    private Boolean checkAuth =false;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final ServiceProjectApp serviceProjectAppImplementation;


    public ClientHandler(Socket socket,DataBaseHandler dataBaseHandler) throws IOException {

        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        int currentCountClient = ServerConnect.countClient++;
        serviceProjectAppImplementation = new ServiceProjectAppImplementation(dataBaseHandler,in, currentCountClient);
        start();

    }

    @Override
    public void run() {
        try {

            workWithProjects();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void workWithProjects() throws IOException{

            while (true) {
                switch (in.readLine()) {

                    case "auth":
                        serviceProjectAppImplementation.findUserInDataBase();
                        break;

                    case "registration":
                        serviceProjectAppImplementation.registrationUser();
                        break;

                    case "signOut":
                        checkAuth = false;
                        break;

                    case "addProject":
                        serviceProjectAppImplementation.addInDatabaseProject();
                        break;

                    case "connectInDb":
                        serviceProjectAppImplementation.connectInDb();
                        break;

                    case "showProjectById":
                        serviceProjectAppImplementation.showProjectById();
                        break;

                    case "searchProjectByNameProject":
                        serviceProjectAppImplementation.searchProject();
                        break;

                    case "deleteProjectByID":
                        serviceProjectAppImplementation.deleteProjectById();
                        break;

                    case "currentUserData":
                        serviceProjectAppImplementation.getFullParamsUser();
                        break;

                    case "updateUserData":
                        serviceProjectAppImplementation.updateUser();
                        break;

                    case "showWorker":

                        serviceProjectAppImplementation.showAllWorkers();
                        break;
                    case "showAllProjects":
                        serviceProjectAppImplementation.showAllProjects();
                        break;

                    case "showAllUser":
                        serviceProjectAppImplementation.showAllUsers();
                        break;

                    case "redactionUser":
                        serviceProjectAppImplementation.redactionUser();
                        break;

                    case "searchTotal":
                        serviceProjectAppImplementation.searchTotal();
                        break;

                    case "statusUser":
                        serviceProjectAppImplementation.statusUser();
                        break;

                    case "addInHistoryTable":
                        serviceProjectAppImplementation.addProjectInDoneDataBase();
                        break;

                    case "showAllProjectsInHistory":
                        serviceProjectAppImplementation.showAllProjectsInDone();
                        break;

            }
        }
    }


    public void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
