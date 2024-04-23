package com.visita.controllers;

import com.visita.models.post;
import com.visita.services.servicePost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class modifypost {

    @FXML
    private TextField modifyPostTitleField;

    @FXML
    private TextArea modifyPostDescriptionArea;

    @FXML
    private ChoiceBox<String> modifyPostTypeChoice;

    @FXML
    private ImageView uploadedImageView;

    private post currentPost;
    private final servicePost sp = new servicePost();

    private String imagePath;

    private String typepost;


    public void initData(post p) {
        // Initialize text fields with current post details
        currentPost = p;
        modifyPostTitleField.setText(p.getTitle_post());
        modifyPostDescriptionArea.setText(p.getContenu_post());
        modifyPostTypeChoice.setValue(p.getType_post());

        ObservableList<String> items = FXCollections.observableArrayList("Medicine", "Heart", "Free Counselling", "Lab Test", "Equipments");
        modifyPostTypeChoice.setItems(items);
        modifyPostTypeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> typepost = newValue);

        if (currentPost.getImage_post() != null) {
            Image oldImage = new Image(currentPost.getImage_post());
            uploadedImageView.setImage(oldImage);
        }
    }

    @FXML
    void handleUploadImageClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        Stage stage = (Stage) modifyPostDescriptionArea.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            uploadedImageView.setImage(image);
            imagePath = file.toURI().toString();
        }
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        // Validate inputs
        if (!validateInputs()) {
            // If validation fails, display error messages
            return;
        }

        // Get modified post details from text fields
        String modifiedTitle = modifyPostTitleField.getText();
        String modifiedType = modifyPostTypeChoice.getValue();
        String modifiedDescription = modifyPostDescriptionArea.getText();

        // Update the current post object with modified details
        currentPost.setTitle_post(modifiedTitle);
        currentPost.setType_post(modifiedType);
        currentPost.setContenu_post(modifiedDescription);

        // Update the image path only if a new image is selected
        if (imagePath != null) {
            currentPost.setImage_post(imagePath);
        }

        // Call the service method to update the post in the database
        sp.modifier(currentPost);

        // Provide feedback to the user that the post has been updated
        Button submitButton = (Button) event.getSource();
        submitButton.setText("Post Updated"); // Change button text
        submitButton.setDisable(true); // Disable the button to prevent multiple submissions
    }

    // Method to validate user inputs
    private boolean validateInputs() {
        String title = modifyPostTitleField.getText();
        String description = modifyPostDescriptionArea.getText();
        String type = modifyPostTypeChoice.getValue();

        // Check title length
        if (title == null || title.length() < 20 ) {
            showError("Title must than 20 characters.");
            return false;
        }

        // Check description length
        if (description == null || description.length() < 30) {
            showError("Description must be more than 30 characters.");
            return false;
        }

        // Check post type selection
        if (type == null || type.isEmpty()) {
            showError("Please select a post type.");
            return false;
        }

        // All inputs are valid
        return true;
    }

    // Method to display error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
