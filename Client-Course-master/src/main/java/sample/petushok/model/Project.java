package sample.petushok.model;

public class Project {

    private int id;
    private String nameProject;
    private double cost;
    private String time;
    private String about;

    public Project(int id, String nameProject, double cost, String time, String about) {
        this.id = id;
        this.nameProject = nameProject;
        this.cost = cost;
        this.time = time;
        this.about = about;
    }

    public Project(int id, String nameProject, double cost, String time) {
        this.id = id;
        this.nameProject = nameProject;
        this.cost = cost;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Project : " +
                "nameProject = " + nameProject +
                ", cost = " + cost +
                ", time = " + time +
                ", about = '" + about + '\''+"\n";
    }
}
