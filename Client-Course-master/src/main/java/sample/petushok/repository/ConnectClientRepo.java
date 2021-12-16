package sample.petushok.repository;

public interface ConnectClientRepo {
    void connectToServer();
    void sendMsg(String message);
    boolean checkUserInDb();

}
