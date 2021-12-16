package server;

public class StartServer  {

    public void startServerConnectToDataBase(){
        ServerConnect serverConnect = new ServerConnect();
        serverConnect.startServer();
        serverConnect.connectNewClientInToServer();
        serverConnect.closeAll();
    }
}
