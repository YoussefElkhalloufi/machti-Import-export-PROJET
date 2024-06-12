package com.example.machti_import_export;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Index.fxml")));
        Scene scene = new Scene(root);

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/appLogo.png")));

        stage.getIcons().add(icon);
        stage.setResizable(false);

        stage.setTitle("Ste Machti");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}