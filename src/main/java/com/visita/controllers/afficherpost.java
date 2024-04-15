package com.visita.controllers;

import com.visita.models.post;
import com.visita.models.comment;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.visita.services.servicePost;
import com.visita.services.serviceComment;

import java.util.List;

public class afficherpost {

    @FXML
    private VBox postContainer; // Container for displaying posts


    private final servicePost sp = new servicePost();
    private final serviceComment sc = new serviceComment();

    public void initialize() {
        // Example: Load posts from a database or other source
        List<post> posts = sp.afficher();

        for (post p : posts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getDescription_post());
            descriptionLabel.getStyleClass().add("description-label");

            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Create a BorderPane to act as the card
            BorderPane card = new BorderPane();
            card.getStyleClass().add("post-card");
            card.setLeft(titleLabel);
            card.setTop(typeLabel);
            card.setLeft(descriptionLabel);
            card.setRight(new StackPane(imageView));

            // Add event handler to show post details when clicked
            card.setOnMouseClicked(event -> showPostDetails(p));

            // Add the card to the postContainer
            postContainer.getChildren().add(card);
        }
    }

    private void showPostDetails(post p) {
        // Clear postContainer to remove previous post details



        postContainer.getChildren().clear();

        // Create labels for title, type, and description
        Label titleLabel = new Label("Title: " + p.getTitle_post());
        titleLabel.getStyleClass().add("title-label");
        Label typeLabel = new Label("Type: " + p.getType_post());
        typeLabel.getStyleClass().add("type-label");
        Label descriptionLabel = new Label("Description: " + p.getDescription_post());
        descriptionLabel.getStyleClass().add("description-label");

        // Create an ImageView for displaying the image
        ImageView imageView = new ImageView(new Image(p.getImage_post()));
        imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
        imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

        // Add all components to a VBox to display them vertically
        VBox postDetails = new VBox(titleLabel, typeLabel, descriptionLabel, imageView);
        postDetails.setAlignment(Pos.CENTER);
        postDetails.setSpacing(10); // Add spacing between components

        // Fetch comments for the selected post
        List<comment> comments = sc.affichersingle(p.getId_post());

        // Display comments
        VBox commentsContainer = new VBox();
        for (comment c : comments) {

                Label commentLabel = new Label("Comment: " + c.getComment());
                commentsContainer.getChildren().add(commentLabel);

        }

        // Input area for adding new comments
        // Replace this with your desired input area implementation
        TextArea commentInput = new TextArea();
        Button addCommentButton = new Button("Add Comment");
        addCommentButton.setOnAction(event -> {
            String newCommentText = commentInput.getText();
            // Add new comment logic here
            sc.ajouter(new comment(1,p.getId_post(),"aaa",newCommentText));
            showPostDetails(p);

        });


        VBox addCommentBox = new VBox(commentInput, addCommentButton);

        // Add all components to a VBox to display post details and comments
        VBox postWithComments = new VBox(postDetails, commentsContainer, addCommentBox);
        postWithComments.setSpacing(10); // Add spacing between components

        // Add the VBox containing post details and comments to the postContainer
        postContainer.getChildren().add(postWithComments);
    }

    @FXML
    private void backToAllPosts() {
        postContainer.getChildren().clear();
        initialize(); // Reload all posts
    }

}
