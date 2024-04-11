package com.visita.controllers;

import com.visita.models.post;
import com.visita.services.servicePost;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class addpost {

    @FXML
    private TextField Post_Title;
    @FXML
    private TextField Post_Type;
    @FXML
    private TextArea Post_Description;
    @FXML
    private ImageView uploadedImageView;

    private String imagePath;

    private final servicePost ps = new servicePost();

    @FXML
    void handleUploadImageClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        Stage stage = (Stage) Post_Title.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            uploadedImageView.setImage(image);
            imagePath = file.toURI().toString(); // Store the file path instead of image URL
        }
    }

    @FXML
    void ajouterPost(ActionEvent event) {
        try {
            if (validateInput()) {
                ps.ajouter(new post(777, Post_Title.getText(), Post_Description.getText(), Post_Type.getText(), imagePath,"aaa"));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Post added successfully");
                alert.showAndWait();
                navigateToNextPage();
            } else {
                showAlert("Error", "Invalid input", "Please fill in all fields and upload an image.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateInput() {
        return !Post_Title.getText().isEmpty() && !Post_Type.getText().isEmpty() && !Post_Description.getText().isEmpty() && imagePath != null;
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void navigateToNextPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpost.fxml"));
        Parent root = loader.load();
        Post_Title.getScene().setRoot(root);
    }

    @FXML
    void handleSubmitButtonClick(ActionEvent event) {
        ajouterPost(event);
    }
}
