package sample.petushok.model;

public class User {

    private String name;
    private String lastName;
    private String password;
    private String email;
    private int roll;
    private String position;



   private int idUser;
   public static User currentUser;


    public User(int idUser,String email, String password, int roll) {
        this.password = password;
        this.email = email;
        this.roll = roll;
        this.idUser = idUser;
    }

    public User(String name, String lastName, String password, String email, int roll, int idUser, String position) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.roll = roll;
        this.position = position;
        this.idUser = idUser;
    }

    public User(String email, int idUser, int roll) {
     this.email = email;
     this.idUser = idUser;
     this.roll = roll;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
