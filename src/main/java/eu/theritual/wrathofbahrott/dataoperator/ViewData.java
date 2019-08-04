package eu.theritual.wrathofbahrott.dataoperator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;

public class ViewData {
    private final FXMLLoader loader;
    private final Group root;

    public ViewData(FXMLLoader loader, Group root) {
        this.loader = loader;
        this.root = root;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Group getRoot() {
        return root;
    }
}