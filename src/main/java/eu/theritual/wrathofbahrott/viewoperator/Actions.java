package eu.theritual.wrathofbahrott.viewoperator;

import javafx.scene.control.Alert;

public class Actions {
    public static void error(String title, String info, String msg) {
        System.out.println(title + " -> " + info + "\n\t" + msg);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(info);
        alert.setContentText(msg);
        if (!alert.isShowing()) {
            alert.show();
        }
    }
}
