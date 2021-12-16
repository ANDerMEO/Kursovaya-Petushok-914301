package sample.petushok.service;

import sample.petushok.controller.constants.ConstParams;

import java.io.*;
import java.net.Socket;


public class Client {

    public static InteractionsWithServer interactionsWithServer;

    public void connectToServer() {
        try {
            Socket clientSocket = new Socket(ConstParams.HOST, ConstParams.PORT);
            interactionsWithServer = new InteractionsWithServer(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
