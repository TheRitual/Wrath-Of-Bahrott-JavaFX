package eu.theritual.wrathofbahrott.viewoperator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

class ViewData {
    private final FXMLLoader loader;
    private final Group root;

    ViewData(FXMLLoader loader, Group root) {
        this.loader = loader;
        this.root = root;
    }

    FXMLLoader getLoader() {
        return loader;
    }

    Group getRoot() {
        return root;
    }
}