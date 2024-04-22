package com.visita.controllers;

import com.visita.models.post;
import com.visita.services.servicePost;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

public class addpost {

    @FXML
    private TextField Post_Title;
    @FXML
    private TextField Phone_Number;
    @FXML
    private TextArea Post_Description;
    @FXML
    private ImageView uploadedImageView;

    @FXML
    private ChoiceBox<String> Typepostchoice;

    @FXML
    private Label errorLabel;


    private String imagePath;
    private String typepost;

    private final servicePost ps = new servicePost();


    @FXML
    public void initialize() {
        // Initialize the choice box and other components
        ObservableList<String> items = FXCollections.observableArrayList("Medicine", "Heart", "Free counselling", "Lab test", "Equipments");
        Typepostchoice.setItems(items);
        Typepostchoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> typepost = newValue);

        // Add listeners for input validation
        Phone_Number.textProperty().addListener((observable, oldValue, newValue) -> validatePhoneNumber(newValue));
        Post_Title.textProperty().addListener((observable, oldValue, newValue) -> validateTitle(newValue));
        Post_Description.textProperty().addListener((observable, oldValue, newValue) -> validateDescription(newValue));

        // Initialize the error label
        errorLabel.setText("");  // Clear any previous error message
    }

    private void validatePhoneNumber(String newValue) {
        // Check if the phone number is a valid integer and at least 8 digits long
        if (newValue.matches("\\d{8,}")) {
            // Input is valid; hide the error label
            Phone_Number.setStyle("-fx-border-color: green; -fx-border-radius: 5px;-fx-border-width: 5px;");
            errorLabel.setVisible(false);
        } else {
            // Input is invalid; show the error label
            if (!newValue.matches("\\d*")) {
                // Not a valid integer
                errorLabel.setText("Phone number must be a valid integer.");
            } else {
                // Valid integer, but not enough digits
                errorLabel.setText("Phone number must be at least 8 digits long.");
            }
            errorLabel.setVisible(true);
            errorLabel.setLayoutX(630);
            errorLabel.setLayoutY(165);
            errorLabel.setStyle("-fx-font-size: 20; -fx-text-fill: red;");
            Phone_Number.setStyle("-fx-border-color: red; -fx-border-radius: 5px; -fx-border-width: 5px;");
        }
    }

    private void validateTitle(String newValue) {
        // Check if the title is at least 20 characters long
        if (newValue.length() >= 20) {
            // Input is valid; hide the error label
            errorLabel.setVisible(false);
            Post_Title.setStyle("-fx-border-color: green; -fx-border-radius: 5px;-fx-border-width: 5px;");
        } else {
            // Input is invalid; show the error label
            errorLabel.setText("Title must be at least 20 characters long.");
            errorLabel.setVisible(true);
            errorLabel.setLayoutX(50);
            errorLabel.setLayoutY(60);
            errorLabel.setStyle("-fx-font-size: 20; -fx-text-fill: red;");
            Post_Title.setStyle("-fx-border-color: red; -fx-border-radius: 5px; -fx-border-width: 5px;");
        }
    }

    private void validateDescription(String newValue) {
        // Check if the description is at least 30 characters long
        if (newValue.length() >= 30) {
            // Input is valid; hide the error label
            errorLabel.setVisible(false);
            Post_Description.setStyle("-fx-border-color: green; -fx-border-radius: 5px; -fx-border-width: 5px;");
        } else {
            // Input is invalid; show the error label
            errorLabel.setText("Description must be at least 30 characters long.");
            errorLabel.setVisible(true);
            errorLabel.setLayoutX(50);
            errorLabel.setLayoutY(400);
            errorLabel.setStyle("-fx-font-size: 20; -fx-text-fill: red;");
            Post_Description.setStyle("-fx-border-color: red; -fx-border-radius: 5px; -fx-border-width: 5px;");
        }
    }

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


/*
    public void convertTextFieldToInt() {
        // Get the text from the TextField
        String text = Phone_Number.getText();

        try {
            // Convert the text to an integer
            int phoneNumber = Integer.parseInt(text);

            // Now you can use the phoneNumber integer in your application
            System.out.println("Converted phone number: " + phoneNumber);
        } catch (NumberFormatException e) {
            // Handle the exception if the text is not a valid integer
            System.out.println("Invalid input: Not a valid integer");
        }
    }*/



    @FXML
    void ajouterPost(ActionEvent event) {
        try {
            if (validateInput()) {
                // Convert the phone number and proceed with adding the post
                int phoneNumber = Integer.parseInt(Phone_Number.getText());
                ps.ajouter(new post(Post_Title.getText(), typepost, Post_Description.getText(), imagePath, phoneNumber, 0));

                // Show a success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Post added successfully");
                alert.showAndWait();

                // Navigate to the next page
                navigateToNextPage();
            } else {
                // Display an alert if the input is invalid
                showAlert("Error", "Invalid input", "Please fix the errors and try again.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateInput() {
        return !Post_Title.getText().isEmpty()  && !Post_Description.getText().isEmpty() && imagePath != null && typepost != null;
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

    @FXML
    void handleShowPostButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherpost.fxml"));
        Parent root = loader.load();
        Post_Title.getScene().setRoot(root);
    }


}
