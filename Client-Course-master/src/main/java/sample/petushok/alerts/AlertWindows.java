package sample.petushok.alerts;

import javafx.scene.control.Alert;

public class AlertWindows {
    public static AlertWindows alertWindows = new AlertWindows();
    private AlertWindows(){}

    public void alertWindowShowWarning(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void alertWindowShowDanger(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
