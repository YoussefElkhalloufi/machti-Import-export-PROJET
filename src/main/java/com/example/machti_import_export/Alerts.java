package com.example.machti_import_export;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public class Alerts {
    public boolean showConfirmationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Create a Font object with the desired font family and size
        Font customFont = Font.font("Gill Sans MT Condensed", 20); // Replace "Your Font Name" with the desired font name

        // Access the Label within the Alert's content area and apply the custom font
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        contentLabel.setFont(customFont);

        // Show the confirmation dialog and wait for user response
        Optional<ButtonType> result = alert.showAndWait();

        // Check which button the user clicked and return true for Yes, false for No
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void showWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Create a Label to display the content
        Label contentLabel = new Label(content);

        // Set font and style
        Font customFont = Font.font("Gill Sans MT Condensed", 18);
        contentLabel.setFont(customFont);
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(Double.MAX_VALUE);
        contentLabel.setMaxHeight(Double.MAX_VALUE);

        // Add contentLabel to alert dialog pane
        alert.getDialogPane().setContent(contentLabel);

        // Load and set icon for the window
        InputStream inputStream = getClass().getResourceAsStream("/images/appLogo.png");
        Image iconImage = new Image(Objects.requireNonNull(inputStream));

        // Set the alert type to ensure the correct icon is displayed in the title bar
        alert.setAlertType(Alert.AlertType.WARNING);

        // Access the stage associated with the alert and set its icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(iconImage);

        alert.showAndWait();
    }



    //MessageWindow when the Company is added succcesfully or when an error is occured during the insert
    public void showAlert(String title, String content, String icon) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Create a Font object with the desired font family and size
        Font customFont = Font.font("Gill Sans MT Condensed", 20); // Replace "Your Font Name" with the desired font name

        // Access the Label within the Alert's content area and apply the custom font
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        contentLabel.setFont(customFont);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(icon)));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(55); // Adjust the height as needed
        imageView.setFitWidth(55);  // Adjust the width as needed

        alert.setGraphic(imageView);

        alert.showAndWait();
    }



}
