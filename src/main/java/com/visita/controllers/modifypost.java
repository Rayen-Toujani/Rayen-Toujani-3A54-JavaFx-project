package com.visita.controllers;

import com.visita.models.post;
import com.visita.services.servicePost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private post currentPost; // Assuming you have a field to hold the current post being modified
    private final servicePost sp = new servicePost(); // Service class instance to perform database operations

    private String imagePath; // Store the file path of the uploaded image

    private String typepost;


    public void initData(post p) {
        // Initialize text fields with current post details
        currentPost = p;
        modifyPostTitleField.setText(p.getTitle_post());
        modifyPostDescriptionArea.setText(p.getContenu_post());
        // Populate other fields as needed
        // For example, if you have a choice box for post type:
        modifyPostTypeChoice.setValue(p.getType_post());

        ObservableList<String> items = FXCollections.observableArrayList("Medicine","Heart","Free counselling","Lab test","Equipments");
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
            imagePath = file.toURI().toString(); // Store the file path instead of image URL
        }
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        // Get modified post details from text fields
        String modifiedTitle = modifyPostTitleField.getText();
        String modifiedType = modifyPostTypeChoice.getValue();
        String modifiedDescription = modifyPostDescriptionArea.getText();

        // Update the currentPost object with modified details
        currentPost.setTitle_post(modifiedTitle);
        currentPost.setType_post(modifiedType);
        currentPost.setContenu_post(modifiedDescription);



        // Update the image path only if a new image is selected
        if (imagePath != null) {
            currentPost.setImage_post(imagePath); // Update the image path
        }



        // Call the service method to update the post in the database
        sp.modifier(currentPost);

        // Optionally, provide feedback to the user that the post has been updated
        // You can display a confirmation message or change the appearance of the submit button

        // For example:
        Button submitButton = (Button) event.getSource();
        submitButton.setText("Post Updated"); // Change button text

        // Disable the submit button to prevent multiple submissions
        submitButton.setDisable(true);
    }
}
