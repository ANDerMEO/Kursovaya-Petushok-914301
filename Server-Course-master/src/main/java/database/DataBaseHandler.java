package database;

import constParams.Const;


import java.sql.*;
import java.util.LinkedList;

public class DataBaseHandler   {
    private  Connection connection;
    private  Statement statement;


    public DataBaseHandler() {
        connectionToDb();
        createTable(connection,statement);
    }



    public void connectionToDb(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(Const.HOST_DATABASE+Const.NAME_DATABASE,
                    Const.USER_DATABASE,
                    Const.PASSWORD_DATABASE);
            statement= connection.createStatement();

            System.out.println("Database connection is done");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTable(Connection connection, Statement statement){
        new TablesDatabase(statement,connection);
    }



    public boolean addUserInDb(String emailDb, String passwordDb, String rollDb,String name,String lastName){
          if(!checkUserIndDb(emailDb)) {
              return false;
          }

            try {
                String query = " insert into users (email, password,roll,name,lastName )"
                        + " values ( ?, ?,?,?,?)";
                
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString (1, emailDb);
                preparedStmt.setString (2, passwordDb);
                preparedStmt.setString (3, rollDb);
                preparedStmt.setString (4, name);
                preparedStmt.setString (5, lastName);


                preparedStmt.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return true;
    }
  
    private boolean checkUserIndDb(String emailUserDb){
        String query = "SELECT " + Const.ID  + " FROM " + Const.USERS_TABLE +
                " WHERE " + Const.EMAIL + " = " + "'" + emailUserDb + "'";
        ResultSet rs = null;
        int idInDb=0;
        try {
            rs = statement.executeQuery(query);
            while (rs.next()) {
               idInDb =  rs.getInt(Const.ID);

            }
            if(idInDb==0){
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public String authUser(String email,String password) {
        String currentUser="";
        try {
            String query = "SELECT " + Const.EMAIL + "," + Const.PASSWORD+","+Const.ID+","+Const.ROLL + " FROM " + Const.USERS_TABLE +
                    " WHERE " + Const.EMAIL + " = " + "'" + email + "'" + " AND " + Const.PASSWORD + " = " + "'" + password + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (!rs.getString(Const.EMAIL).equals("") &&
                        !rs.getString(Const.PASSWORD).equals("")) {
                    currentUser+=rs.getString(Const.ID)+" ";
                    currentUser+=rs.getString(Const.ROLL);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(currentUser.equals("")) {
            return "false";
        }
        else {
            return currentUser;
        }
    }

    public boolean addProjectInDb(int userIdDb, String nameProjectDb , double costDb, String timeDb , String aboutDb){

        try {
            String query = "INSERT INTO " +Const.PROJECTS_TABLE+ " (userId, nameProject, cost, time, about)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt (1, userIdDb);
            preparedStmt.setString (2, nameProjectDb);
            preparedStmt.setDouble (3, costDb);
            preparedStmt.setString (4, timeDb);
            preparedStmt.setString (5, aboutDb);




            preparedStmt.executeUpdate();
 String query1 = "INSERT INTO " + Const.TOTAL_PROJECTS + "(userId, nameProject, cost, time, about) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt1 = connection.prepareStatement(query1);

            preparedStmt1.setInt (1, userIdDb);
            preparedStmt1.setString (2, nameProjectDb);
            preparedStmt1.setDouble (3, costDb);
            preparedStmt1.setString (4, timeDb);
            preparedStmt1.setString (5, aboutDb);
            preparedStmt1.executeUpdate();


            System.out.println("Данные в "+Const.PROJECTS_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        double cost = costDb;
//
//        addInTotal(countModel(),cost);


        return true;
    }

    public boolean connectInDb(int userIdDb, String nameProjectDb , double costDb, String timeDb , String aboutDb){

        try {
            String query = "INSERT INTO " +Const.TOTAL_PROJECTS + " (userId, nameProject, cost, time, about)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt (1, userIdDb);
            preparedStmt.setString (2, nameProjectDb);
            preparedStmt.setDouble (3, costDb);
            preparedStmt.setString (4, timeDb);
            preparedStmt.setString (5, aboutDb);


            preparedStmt.executeUpdate();




            System.out.println("Данные в "+Const.TOTAL_PROJECTS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        double cost = costDb;
//
//        addInTotal(countModel(),cost);


        return true;
    }

    private void addInTotal(int id,double costTotal){
        try {
            String query = " insert into " +Const.TOTAL_PROJECTS + " (idProject, costTotal)"
                    + " values ( ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt (1, id);
            preparedStmt.setDouble (2, costTotal);


            preparedStmt.executeUpdate();




            System.out.println("Данные в "+Const.TOTAL_PROJECTS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public LinkedList<String> showProjectsById(int idUser){

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Const.ID_PROJECT_TOTAL+" , " + Const.NAME_PROJECT_TOTAL+" , "+Const.COST_TOTAL+" , "
                +Const.TIME_TOTAL+" ," +Const.ABOUT_PROJECT_TOTAL+ " FROM " + Const.TOTAL_PROJECTS +
                " WHERE " + Const.USER_ID_TOTAL + " = " + "'" + idUser + "'";
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {


                project+=rs.getString(Const.ID_PROJECT_TOTAL)+" ";
                project+=rs.getString(Const.NAME_PROJECT_TOTAL)+" ";
                project+=rs.getString(Const.COST_TOTAL)+" ";
                project+=rs.getString(Const.TIME_TOTAL)+" ";
                project+=rs.getString(Const.ABOUT_PROJECT_TOTAL)+" ";
                list.add(project);

                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showProjectsByIdAdmin(int id){

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Const.ID_PROJECT+" , " + Const.NAME_PROJECT+" , "+Const.COST+" , "
                +Const.TIME+" FROM " + Const.PROJECTS_TABLE +
                " WHERE " + Const.USER_ID + " = " + "'" + id + "'";
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                project+=rs.getString(Const.ID_PROJECT)+" ";
                project+=rs.getString(Const.NAME_PROJECT)+" ";
                project+=rs.getString(Const.COST)+" ";
                project+=rs.getString(Const.TIME)+" ";

                list.add(project);

                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showProjectByName(String nameDb,int idUser){
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Const.ID_PROJECT+" , " + Const.NAME_PROJECT+" , "+Const.COST+" , "
                +Const.TIME+" ," +Const.ABOUT_PROJECT+" FROM " + Const.PROJECTS_TABLE +
                " WHERE " + Const.NAME_PROJECT + " = " + "'" + nameDb + "'" +" AND "+ Const.USER_ID + " = " + "'" + idUser + "'" ;
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                project+=rs.getString(Const.ID_PROJECT)+" ";
                project+=rs.getString(Const.NAME_PROJECT)+" ";
                project+=rs.getString(Const.COST)+" ";
                project+=rs.getString(Const.TIME)+" ";
                project+=rs.getString(Const.ABOUT_PROJECT)+" ";
                list.add(project);
                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public int countModel()  {
     LinkedList<String> listContribution = showAllProjects();
        String[] subStr;
        subStr = listContribution.get(listContribution.size()-1).split(" ");


     return Integer.parseInt(subStr[0]);
    }

    public void deleteProjectByName(int id)  {

        String selectSQL = "DELETE FROM "+Const.PROJECTS_TABLE +  " WHERE id = ?";
        try {
            connection.prepareStatement(selectSQL);
            PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String totalProject(int id){
        String selectSQL = "SELECT "+Const.COST_TOTAL +" FROM "+Const.TOTAL_PROJECTS  +  " WHERE idProject = "+ "'" + id + "'" ;
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(selectSQL);

            while (rs.next()) {
                project+=rs.getString(Const.COST_TOTAL);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return project;

    }

    public String currentUserData(int id){
          Connection connection = null;
          Statement statement = null;

        try {
            connection = this.connection;
            statement= connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String currentUser="";
        try {
            String query = "SELECT " + Const.NAME_USER + " , " + Const.LAST_NAME_USER+" , "+Const.PASSWORD +" , "+Const.EMAIL+" , "+Const.ROLL+ " FROM " + Const.USERS_TABLE +
                    " WHERE " + Const.ID + " = " + "'" + id + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                    currentUser+=rs.getString(Const.NAME_USER)+" ";
                    currentUser+=rs.getString(Const.LAST_NAME_USER)+" ";
                    currentUser+=rs.getString(Const.PASSWORD)+" ";
                    currentUser+=rs.getString(Const.EMAIL)+" ";
                    currentUser+=rs.getString(Const.ROLL)+" ";
                    currentUser+=id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return currentUser;

    }

    public  void updateUser(int id,String name ,String lastName,String email ,String password)  {

        String query = "UPDATE users_bank SET name  = ?, lastName = ? ,email = ?,password = ? WHERE id = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString   (1, name);
            preparedStmt.setString(2, lastName);
            preparedStmt.setString(3, email);
            preparedStmt.setString(4, password);
            preparedStmt.setInt(5, id);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void statusUser(int id ,String position){
        try {
            String query = " insert into " +Const.WORKER_TABLE + " (idUserWorker,position )"
                    + " values ( ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setInt (1, id);
            preparedStmt.setString (2, position);


            preparedStmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public LinkedList<String>  showAllWorkers(){
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT " + Const.ID_USER_WORKER+" , "+Const.POSITION+ " FROM " + Const.WORKER_TABLE ;

        
        String contribution="";
        try {
            ResultSet rs  = statement.executeQuery(query);

            while (rs.next()) {
                
                contribution+=currentUserData(Integer.parseInt(rs.getString(Const.ID_USER_WORKER)))+" ";
                contribution+=rs.getString(Const.POSITION);
                System.out.println(contribution);
                list.add(contribution);

                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public LinkedList<String> showAllProjects() {
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Const.ID_PROJECT+" , " + Const.NAME_PROJECT+" , "+Const.COST+" , "
                 +Const.TIME+" , "+ Const.ABOUT_PROJECT+ " FROM " + Const.PROJECTS_TABLE ;
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                project+=rs.getString(Const.ID_PROJECT)+" ";
                project+=rs.getString(Const.NAME_PROJECT)+" ";
                project+=rs.getString(Const.COST)+" ";
                project+=rs.getString(Const.TIME)+" ";
                project+=rs.getString(Const.ABOUT_PROJECT)+" ";
                list.add(project);
                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;


    }

    public LinkedList<String> showAllUsers() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Const.ID+" , " + Const.EMAIL+" , "+Const.PASSWORD+" , "
                +Const.ROLL+ " FROM " + Const.USERS_TABLE ;
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                project+=rs.getString(Const.ID)+" ";
                project+=rs.getString(Const.EMAIL)+" ";
                project+=rs.getString(Const.PASSWORD)+" ";
                project+=rs.getString(Const.ROLL)+" ";
                list.add(project);
                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public void redactionUser(int idUser, String email,String password,int roll) {
        String query = "update "+Const.USERS_TABLE+" SET   email =?, password =?, roll=? WHERE " + Const.ID + " = " + "'" + idUser + "'";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, email);
            preparedStmt.setString(2, password);
            preparedStmt.setInt   (3, roll);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void addDoneProject(int id){
        try{

            String query = " insert into " +Const.DONE_TABLE + " (idProject)"
                    + " values (?);" + "delete from " + Const.PROJECTS_TABLE
                    + " WHERE id IN (select idProject from " + Const.DONE_TABLE + ")";

        PreparedStatement preparedStmt = connection.prepareStatement(query);

        preparedStmt.setInt (1, id);

        preparedStmt.executeUpdate();



    } catch (SQLException throwables) {
        throwables.printStackTrace();
     }
    }

    public LinkedList<String> showHistory(){
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Const.ID_DONE+ " FROM " + Const.DONE_TABLE ;
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                project+=rs.getString(Const.ID_DONE);
                list.add(project);
                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void showProjectByIdDone(LinkedList<String> list , int id){

        String query = "SELECT "+Const.ID_PROJECT+" , " + Const.NAME_PROJECT+" , "+Const.COST+" , "
                +Const.TIME+" ," +Const.ABOUT_PROJECT+" FROM " + Const.PROJECTS_TABLE +
                " WHERE " + Const.ID_PROJECT + " = " + "'" + id + "'";
        ResultSet rs = null;
        String project="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                project+=rs.getString(Const.ID_PROJECT)+" ";
                project+=rs.getString(Const.NAME_PROJECT)+" ";
                project+=rs.getString(Const.COST)+" ";
                project+=rs.getString(Const.TIME)+" ";
                project+=rs.getString(Const.ABOUT_PROJECT)+" ";

                list.add(project);

                project="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
