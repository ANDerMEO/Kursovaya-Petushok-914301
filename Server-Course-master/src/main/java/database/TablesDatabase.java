package database;

import constParams.Const;

import java.sql.*;

public class TablesDatabase {
     private final Statement statement;
     private final Connection connection;

    public TablesDatabase(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;

        createTableWorker();
        createTotalProject();
        createTableDoneTable();
        addTableUserInDateBase();
        addTableProjects();
    }

    public void createTableWorker(){

        if(tableExists(Const.WORKER_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Const.WORKER_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " idUserWorker INTEGER, " +
                        " position VARCHAR (50) " +
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Const.WORKER_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTotalProject(){
        if(tableExists(Const.TOTAL_PROJECTS)) {
            try {
                String SQL = "CREATE TABLE "+Const.TOTAL_PROJECTS +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " userId INTEGER ,"+
                        " nameProject VARCHAR (50), " +
                        " cost DOUBLE PRECISION, " +
                        " time VARCHAR (50),"+
                        " about VARCHAR (200) "+
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Const.TOTAL_PROJECTS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableDoneTable(){

        if(tableExists(Const.DONE_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Const.DONE_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " idProject INTEGER, " +
                        "nameProject VARCHAR (50)," +
                        "costProject DOUBLE PRECISION" +

                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Const.DONE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTableUserInDateBase(){
        if(tableExists(Const.USERS_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Const.USERS_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " email VARCHAR (50), " +
                        " name VARCHAR (50), " +
                        " lastName VARCHAR (50), " +
                        " password VARCHAR (50), " +
                        " roll VARCHAR (50)"+
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица с users была создана ! "+Const.USERS_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTableProjects(){
        if(tableExists(Const.PROJECTS_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Const.PROJECTS_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " userId INTEGER ,"+
                        " nameProject VARCHAR (50), " +
                        " cost DOUBLE PRECISION, " +
                        " time VARCHAR (50),"+
                        " about VARCHAR (200) "+

                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица с users была создана ! "+Const.PROJECTS_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean tableExists(String nameTable){

        try{
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, nameTable, null);
            rs.last();
            return rs.getRow() <= 0;
        }catch(SQLException ignored){

        }
        return true;
    }
}
