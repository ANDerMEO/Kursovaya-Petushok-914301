package repository;

import java.io.IOException;

public interface ServiceProjectApp {
   void findUserInDataBase();
   void registrationUser();
   void addInDatabaseProject();
   void showProjectById() throws IOException;
   void searchProject() throws IOException;
   void deleteProjectById();
   void getFullParamsUser();
   void updateUser();
   void showAllWorkers();
   void showAllProjects();
   void showAllUsers();
   void redactionUser();
   void searchTotal();
   void addProjectInDoneDataBase();
   void showAllProjectsInDone();
   void statusUser();
   void connectInDb();

}
