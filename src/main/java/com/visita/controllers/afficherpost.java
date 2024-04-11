package com.visita.controllers;

import com.visita.models.post;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.visita.services.servicePost;

import java.util.List;

public class afficherpost {

    @FXML
    private VBox postContainer; // Container for displaying posts

    private final servicePost sp = new servicePost();

    public void initialize() {
        // Example: Load posts from a database or other source
        List<post> posts = sp.afficher();

        for (post p : posts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            Label typeLabel = new Label("Type: " + p.getType_post());
            Label descriptionLabel = new Label("Description: " + p.getDescription_post());

            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Add all components to a VBox to display them vertically
            VBox postContent = new VBox(titleLabel, typeLabel, descriptionLabel, imageView);
            postContent.setSpacing(10); // Add spacing between components

            // Add the VBox containing post components to the postContainer
            postContainer.getChildren().add(postContent);
        }
    }
}
