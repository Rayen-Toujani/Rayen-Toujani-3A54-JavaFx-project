package com.visita.controllers;

import com.visita.models.post;
import com.visita.models.comment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class afficherpost {

    private int loggedInUserId = 777;

    private int currentPage = 1; // Initialize current page to 1
    private int pageSize = 10; // Set page size to 10 posts per page
    private int totalPosts; // Total number of posts
    private int totalPages; // Total number of pages

    @FXML
    private VBox postContainer; // Container for displaying posts


    @FXML
    private Button previousPageButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private Label pageInfoLabel;


    private final servicePost sp = new servicePost();
    private final serviceComment sc = new serviceComment();






    public void initialize() {
        // Calculate total number of posts
        totalPosts = sp.countTotalPosts();

        // Update total pages
        updateTotalPages();

        // Example: Load posts for the current page
        loadPosts(currentPage);
    }

    @FXML
    private void loadPosts(int page) {
        // Calculate offset based on page number and page size
        int offset = (page - 1) * pageSize;
        // Load posts for the specified page
        List<post> posts = sp.getPostsForPage(offset, pageSize);
        // Display the loaded posts
        displayPosts(posts);
    }


    @FXML
    private void goToPreviousPage() {
        System.out.println("Previous Page Button Clicked");
        if (currentPage > 1) {
            currentPage--;
            loadPosts(currentPage);
            updateTotalPages(); // Update total pages
            updatePaginationUI(); // Update pagination UI

        }
        postContainer.getChildren().clear();
        initialize();
    }

    @FXML
    private void goToNextPage() {
        System.out.println("Next Page Button Clicked");
        if (currentPage < totalPages) {
            currentPage++;
            loadPosts(currentPage);
            updateTotalPages(); // Update total pages
            updatePaginationUI(); // Update pagination UI

        }
        postContainer.getChildren().clear();
        initialize();
    }



    @FXML
    private void updatePaginationUI() {
        // Update page information label
        pageInfoLabel.setText("Page " + currentPage + " of " + totalPages);

        // Disable previous page button if on the first page
        previousPageButton.setDisable(currentPage == 1);

        // Disable next page button if on the last page
        nextPageButton.setDisable(currentPage == totalPages);
    }


    private void updateTotalPages() {
        // Recalculate total number of pages
        totalPages = (int) Math.ceil((double) totalPosts / pageSize);
    }




    private void displayPosts(List<post> posts) {
        // Iterate through the list of posts and display each one
        for (post p : posts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getDescription_post());
            descriptionLabel.getStyleClass().add("description-label");

            Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId_post()));
            likesLabel.getStyleClass().add("likes-label");


            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Create a BorderPane to act as the card
            BorderPane card = new BorderPane();
            card.getStyleClass().add("post-card");
            card.setLeft(titleLabel);
            card.setTop(typeLabel);
            card.setBottom(descriptionLabel);
            card.setRight(new StackPane(imageView));

            BorderPane.setAlignment(likesLabel, Pos.BOTTOM_RIGHT);
            card.setBottom(likesLabel);

            // Add event handler to show post details when clicked
            card.setOnMouseClicked(event -> showPostDetails(p));

            // Add the card to the postContainer
            postContainer.getChildren().add(card);
        }
    }

    // Inside afficherpost controller

    private void updateLikeButtonState(Button likeButton, int postId) {
        if (sp.hasLikedPost(postId, loggedInUserId)) {
            likeButton.setText("Unlike");
        } else {
            likeButton.setText("Like");
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

        // Create a label to display the number of likes
        Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId_post()));
        likesLabel.getStyleClass().add("likes-label");

        // Create an ImageView for displaying the image
        ImageView imageView = new ImageView(new Image(p.getImage_post()));
        imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
        imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

        // Add all components to a VBox to display them vertically
        VBox postDetails = new VBox(titleLabel, typeLabel, descriptionLabel, imageView, likesLabel);
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
            // Filter the new comment
            newCommentText = CommentFilter.filterComment(newCommentText);
            // Add new comment logic here
            sc.ajouter(new comment(1, p.getId_post(), "aaa", newCommentText));
            showPostDetails(p);
        });

        VBox addCommentBox = new VBox(commentInput, addCommentButton);

        // Like Button
        Button likeButton = new Button();
        updateLikeButtonState(likeButton, p.getId_post());

        likeButton.setOnAction(event -> {
            if (!sp.hasLikedPost(p.getId_post(), loggedInUserId)) {
                // If the user hasn't liked the post yet, add a like
                boolean success = sp.addLike(p.getId_post(), loggedInUserId);
                if (success) {
                    // Update the likes count and label
                    p.setLikes_post(p.getLikes_post() + 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId_post()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Unlike");
                }
            } else {
                // If the user has already liked the post, remove the like
                boolean success = sp.removeLike(p.getId_post(), loggedInUserId);
                if (success) {
                    // Update the likes count and label
                    p.setLikes_post(p.getLikes_post() - 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId_post()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Like");
                }
            }
        });

        // Add the like button and likes label to the VBox containing post details and comments
        VBox postWithComments = new VBox(postDetails, commentsContainer, addCommentBox, likeButton);
        postWithComments.setSpacing(10); // Add spacing between components

        // Add the VBox containing post details and comments to the postContainer
        postContainer.getChildren().add(postWithComments);
    }


    private void showPostDetailsuser(post p) {
        // Clear postContainer to remove previous post details



        postContainer.getChildren().clear();

        // Create labels for title, type, and description
        Label titleLabel = new Label("Title: " + p.getTitle_post());
        titleLabel.getStyleClass().add("title-label");
        Label typeLabel = new Label("Type: " + p.getType_post());
        typeLabel.getStyleClass().add("type-label");
        Label descriptionLabel = new Label("Description: " + p.getDescription_post());
        descriptionLabel.getStyleClass().add("description-label");


        Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId_post()));
        likesLabel.getStyleClass().add("likes-label");


        // Create an ImageView for displaying the image
        ImageView imageView = new ImageView(new Image(p.getImage_post()));
        imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
        imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

        // Add all components to a VBox to display them vertically
        VBox postDetails = new VBox(titleLabel, typeLabel, descriptionLabel, imageView ,likesLabel);
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

        // Assuming you have a constant image path

        Button deleteButton = new Button("Delete Post");
        deleteButton.setOnAction(event -> {
            // Delete the post logic here
            sp.Supprimer(p.getId_post()); // Replace deletePost with your actual method
            // Refresh posts after deletion
            showUserPosts();
        });

        Button modifyButton = new Button("Modify Post");
        modifyButton.setOnAction(event -> {
            // Handle modify button click event
            // Open a window or dialog for modifying the post details
        });


        // Add the delete button to the VBox containing post details and comments
        postWithComments.getChildren().add(deleteButton);

        postWithComments.getChildren().add(modifyButton);

        modifyButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifypost.fxml"));
                Parent root = loader.load();

                // Pass the selected post object to the modify post controller
                modifypost modifyPostController = loader.getController();
                modifyPostController.initData(p);

                // Add event handler for when the modification window closes
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> {
                    // Refresh post details after modification window is closed
                    showPostDetailsuser(p);
                });
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        // Like Button
        Button likeButton = new Button();
        updateLikeButtonState(likeButton, p.getId_post());

        likeButton.setOnAction(event -> {
            if (!sp.hasLikedPost(p.getId_post(), loggedInUserId)) {
                // If the user hasn't liked the post yet, add a like
                boolean success = sp.addLike(p.getId_post(), loggedInUserId);
                if (success) {



                    p.setLikes_post(p.getLikes_post() + 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId_post()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Unlike");




                }
            } else {
                // If the user has already liked the post, remove the like
                boolean success = sp.removeLike(p.getId_post(), loggedInUserId);
                if (success) {



                    p.setLikes_post(p.getLikes_post() - 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId_post()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Like");




                }
            }
        });

// Add the like button to the VBox containing post details and comments
        postWithComments.getChildren().add(likeButton);


    }




    @FXML
    private void showUserPosts() {
        // Example: Load posts by the logged-in user from a database or other source
        List<post> userPosts = sp.afficheruserpost(loggedInUserId); // Replace getPostsByUser with your actual method

        // Clear postContainer to remove previous post details
        postContainer.getChildren().clear();

        for (post p : userPosts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getDescription_post());
            descriptionLabel.getStyleClass().add("description-label");
            Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId_post()));
            likesLabel.getStyleClass().add("likes-label");


            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Create a BorderPane to act as the card
            BorderPane card = new BorderPane();
            card.getStyleClass().add("post-card");
            card.setLeft(titleLabel);
            card.setTop(typeLabel);
            card.setBottom(descriptionLabel);
            card.setRight(new StackPane(imageView));

            BorderPane.setAlignment(likesLabel, Pos.BOTTOM_RIGHT);
            card.setBottom(likesLabel);

            // Add event handler to show post details when clicked
            card.setOnMouseClicked(event -> showPostDetailsuser(p));

            // Add the delete button for each post
            Button deleteButton = new Button("Delete Post");
            deleteButton.setOnAction(event -> {
                // Delete the post logic here
                sp.Supprimer(p.getId_post()); // Replace deletePost with your actual method
                // Refresh posts after deletion
                showUserPosts();
            });

            // Create a VBox to hold the card and delete button
            VBox postWithDeleteButton = new VBox(card);
            postWithDeleteButton.setSpacing(10); // Add spacing between components

            // Add the VBox containing the card and delete button to the postContainer
            postContainer.getChildren().add(postWithDeleteButton);
        }
    }


    private int whereami;

    @FXML
    private void backToAllPosts() {
        postContainer.getChildren().clear();
        initialize(); // Reload all posts
        whereami =1;
    }

    @FXML
    private void showUserPostsButtonClicked() {
        // Call the method to display the user's posts
        showUserPosts();
        whereami =0;

    }


}
