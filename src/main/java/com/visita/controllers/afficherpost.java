package com.visita.controllers;

import com.visita.models.post;
import com.visita.models.comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.visita.services.servicePost;
import com.visita.services.serviceComment;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class afficherpost {

    private int loggedInUserId = 777;

    private int currentPage = 1; // Initialize current page to 1
    private int pageSize = 10; // Set page size to 10 posts per page
    private int totalPosts; // Total number of posts
    private int totalPages; // Total number of pages

    @FXML
    private VBox postContainer; // Container for displaying posts

    @FXML
    private ChoiceBox<String> choiceres;

    @FXML
    private TextField searchBar;
    @FXML
    private BorderPane parent;



    @FXML
    private Button previousPageButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private Label pageInfoLabel;

    @FXML
    private DatePicker startDatePicker; // DatePicker for start date

    @FXML
    private DatePicker endDatePicker; // DatePicker for end date

    @FXML
    private TextField userField; // TextField for user filter

    @FXML
    private TextField locationField; // TextField for location filter

    @FXML
    private ChoiceBox<String> sortOptions; // ChoiceBox for sorting options

    @FXML
    private ToggleButton changemodebtn;


    private final servicePost sp = new servicePost();
    private final serviceComment sc = new serviceComment();


    private int test=0;

    @FXML
    private ToggleButton modeToggleButton;
    @FXML
    private ImageView img_mode;



    private boolean isLightMode = true;

    public void changemode(ActionEvent event){
        isLightMode= !isLightMode;
        if (isLightMode){
            setLightMode();
        }else {
            setDarkMode();
        }
    }


    private void setLightMode() {
        parent.getStylesheets().remove("dark.css");
        parent.getStylesheets().add("style.css");

        try {
            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-moon-100.png");
            img_mode.setImage(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found: " + e.getMessage());
            // Handle error: set a default image or show an error message
        }
    }

    private void setDarkMode() {
        parent.getStylesheets().remove("style.css");
        parent.getStylesheets().add("dark.css");

        try {
            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-sun-100.png");
            img_mode.setImage(image);
        } catch (IllegalArgumentException e) {
            System.err.println("Image not found: " + e.getMessage());
            // Handle error: set a default image or show an error message
        }
    }






    public void initialize() {

        modeToggleButton.getStyleClass().add("toggle-switch");









        if (test==0) {
            // Initialize sorting options
            sortOptions.getItems().addAll();
        test=1;
        }
            sortOptions.setValue("Date"); // Set default sorting option
        // Add event handler to search bar and other components
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        userField.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        locationField.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        sortOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchPosts());
        // Calculate total number of posts
        totalPosts = sp.countTotalPosts();

        // Update total pages
        updateTotalPages();

        // Example: Load posts for the current page
        loadPosts(currentPage);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> searchPosts());



    }





    @FXML
    private void searchPosts() {
        // Get the search keyword
        String keyword = searchBar.getText() != null ? searchBar.getText().toLowerCase() : "";

        // Get the start date and end date
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // Initialize a date formatter to format LocalDate to String
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

        // Convert LocalDate to String
        String startDateStr = startDate != null ? startDate.format(dateFormatter) : null;
        String endDateStr = endDate != null ? endDate.format(dateFormatter) : null;

        // Get the user and location filters
        String user = userField.getText() != null ? userField.getText().toLowerCase() : "";
        String location = locationField.getText() != null ? locationField.getText() : "";

        // Get the sort option
        String sortBy = sortOptions.getValue();

        // If no sort option is selected, you can provide a default
        if (sortBy == null) {
            sortBy = "date";  // Default to sorting by date if no other option is selected
        }

        // Call the search and filter method and get the filtered posts
        List<post> filteredPosts = sp.searchAndFilterPosts(keyword, startDateStr, endDateStr, user, location, sortBy);

        // Display the filtered posts
        displayPosts(filteredPosts);
    }

    @FXML
    private void loadPosts(int page) {
        // Calculate offset based on page number and page size
        int offset = (page - 1) * pageSize;
        // Load validated posts for the specified page
        List<post> posts = sp.getValidatedPostsForPage(offset, pageSize);
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

        System.out.println(sp.countLikesForPost(77));

        postContainer.getChildren().clear();
        // Iterate through the list of posts and display each one
        for (post p : posts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getContenu_post());
            descriptionLabel.getStyleClass().add("description-label");

            // Create additional labels for date posted and country
            Label dateLabel = new Label("Date: " + p.getMakedate_post());
            dateLabel.getStyleClass().add("date-label");
            Label countryLabel = new Label("Country: " + p.getCountry());
            countryLabel.getStyleClass().add("country-label");

            Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
            likesLabel.getStyleClass().add("likes-label");

            // Create an ImageView for displaying the image
            ImageView imageView = new ImageView(new Image(p.getImage_post()));
            imageView.setFitWidth(200); // Set width to 200 (adjust as needed)
            imageView.setFitHeight(200); // Set height to 200 (adjust as needed)

            // Create a BorderPane to act as the card
            BorderPane card = new BorderPane();
            card.getStyleClass().add("post-card");

            // Set left, top, and right contents
            VBox leftContainer = new VBox(titleLabel, typeLabel, dateLabel, countryLabel);
            card.setLeft(leftContainer);
            card.setTop(typeLabel);
            card.setRight(new StackPane(imageView));

            // Set bottom content
            VBox bottomContainer = new VBox(descriptionLabel, likesLabel);
            card.setBottom(bottomContainer);
            //BorderPane.setAlignment(likesLabel, Pos.BOTTOM_RIGHT);

            // Add event handler to show post details when clicked
            card.setOnMouseClicked(event -> showPostDetails(p));

            // Add the card to the postContainer
            postContainer.getChildren().add(card);


            // Optionally, set preferred width and margin for the card
            card.setPrefWidth(300); // Adjust the card width as needed
            card.setStyle("-fx-margin-left: 10; -fx-margin-right: 10;"); // Adjust left and right margin as needed
        }
    }

    // Inside afficherpost controller

    private void updateLikeButtonState(Button likeButton, int postId) {
        if (sp.hasLikedPost(postId, loggedInUserId)) {
            //likeButton.setText("Unlike");
            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-love-96.png");

            // Create an ImageView from the image
            ImageView imageView = new ImageView(image);

            // Optionally, set the size of the ImageView
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);

            // Set the ImageView as the graphic for the button
            likeButton.setGraphic(imageView);
        } else {

            Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-heart-96.png");

            // Create an ImageView from the image
            ImageView imageView = new ImageView(image);

            // Optionally, set the size of the ImageView
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);

            // Set the ImageView as the graphic for the button
            likeButton.setGraphic(imageView);

        }
    }


    private void showPostDetails(post p) {
        // Clear postContainer to remove previous post details
        System.out.println(p.getId());
        postContainer.getChildren().clear();

        // Create labels for title, type, and description
        Label titleLabel = new Label("Title: " + p.getTitle_post());
        titleLabel.getStyleClass().add("title-label");
        Label typeLabel = new Label("Type: " + p.getType_post());
        typeLabel.getStyleClass().add("type-label");
        Label descriptionLabel = new Label("Description: " + p.getContenu_post());
        descriptionLabel.getStyleClass().add("description-label");

        // Create a label to display the number of likes
        Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
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
        List<comment> comments = sc.affichersingle(p.getId());

        // Display comments
        VBox commentsContainer = new VBox();
        for (comment c : comments) {
            Label commentLabel = new Label("Comment: " + c.getContenu_comment());
            commentsContainer.getChildren().add(commentLabel);
        }



        // Input area for adding new comments
        // Replace this with your desired input area implementation
        TextArea commentInput = new TextArea();
        Button addCommentButton = new Button("Add Comment");
        addCommentButton.getStyleClass().add("login_button");
        addCommentButton.setOnAction(event -> {
            String newCommentText = commentInput.getText();
            // Filter the new comment
            newCommentText = CommentFilter.filterComment(newCommentText);
            // Add new comment logic here
            sc.ajouter(new comment(1,p.getId(),"azea",newCommentText));
            showPostDetails(p);
        });

        VBox addCommentBox = new VBox(commentInput, addCommentButton);

        // Like Button
        Button likeButton = new Button();
        updateLikeButtonState(likeButton, p.getId());

        likeButton.setOnAction(event -> {
            if (!sp.hasLikedPost(p.getId(), loggedInUserId)) {
                // If the user hasn't liked the post yet, add a like
                boolean success = sp.addLike(p.getId(), loggedInUserId);
                if (success) {
                    // Update the likes count and label
                    p.setLikes_post(p.getLikes_post() + 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                   // likeButton.setText("Unlike");
                    Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-love-96.png");

                    // Create an ImageView from the image
                    ImageView imageVieww = new ImageView(image);

                    // Optionally, set the size of the ImageView
                    imageVieww.setFitHeight(40);
                    imageVieww.setFitWidth(40);

                    // Set the ImageView as the graphic for the button
                    likeButton.setGraphic(imageVieww);

                }
            } else {
                // If the user has already liked the post, remove the like
                boolean success = sp.removeLike(p.getId(), loggedInUserId);
                if (success) {
                    // Update the likes count and label
                    p.setLikes_post(p.getLikes_post() - 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                    Image image = new Image("file:/C:/Users/rayen/IdeaProjects/startfromthebottom/secondtryjavapidev/src/main/resources/values/icons8-heart-96.png");

                    // Create an ImageView from the image
                    ImageView imageVieww = new ImageView(image);

                    // Optionally, set the size of the ImageView
                    imageVieww.setFitHeight(40);
                    imageVieww.setFitWidth(40);

                    // Set the ImageView as the graphic for the button
                    likeButton.setGraphic(imageVieww);
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

        System.out.println("\n" +p.getId());

        postContainer.getChildren().clear();

        // Create labels for title, type, and description
        Label titleLabel = new Label("Title: " + p.getTitle_post());
        titleLabel.getStyleClass().add("title-label");
        Label typeLabel = new Label("Type: " + p.getType_post());
        typeLabel.getStyleClass().add("type-label");
        Label descriptionLabel = new Label("Description: " + p.getContenu_post());
        descriptionLabel.getStyleClass().add("description-label");


        Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
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
        List<comment> comments = sc.affichersingle(p.getId());

        // Display comments
        VBox commentsContainer = new VBox();
        for (comment c : comments) {

            Label commentLabel = new Label("Comment: " + c.getContenu_comment());
            commentsContainer.getChildren().add(commentLabel);

        }

        // Input area for adding new comments
        // Replace this with your desired input area implementation
        TextArea commentInput = new TextArea();
        Button addCommentButton = new Button("Add Comment");
        addCommentButton.getStyleClass().add("login_button");
        addCommentButton.setOnAction(event -> {
            String newCommentText = commentInput.getText();
            // Add new comment logic here
            sc.ajouter(new comment(1, p.getId(), "aaa",newCommentText));
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
        deleteButton.getStyleClass().add("login_button");
        deleteButton.setOnAction(event -> {
            // Delete the post logic here
            sp.Supprimer(p.getId()); // Replace deletePost with your actual method
            System.out.println(p.getId());
            // Refresh posts after deletion
            showUserPosts();
        });

        Button modifyButton = new Button("Modify Post");
        modifyButton.getStyleClass().add("login_button");
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
        updateLikeButtonState(likeButton, p.getId());

        likeButton.setOnAction(event -> {
            if (!sp.hasLikedPost(p.getId(), loggedInUserId)) {
                // If the user hasn't liked the post yet, add a like
                boolean success = sp.addLike(p.getId(), loggedInUserId);
                if (success) {



                    p.setLikes_post(p.getLikes_post() + 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
                    // Update UI or provide feedback to the user
                    likeButton.setText("Unlike");




                }
            } else {
                // If the user has already liked the post, remove the like
                boolean success = sp.removeLike(p.getId(), loggedInUserId);
                if (success) {



                    p.setLikes_post(p.getLikes_post() - 1);
                    likesLabel.setText("Likes: " + sp.countLikesForPost(p.getId()));
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
        List<post> userPosts = sp.getValidatedUserPosts(loggedInUserId); // Replace getPostsByUser with your actual method


        // Clear postContainer to remove previous post details
        postContainer.getChildren().clear();

        for (post p : userPosts) {
            // Create labels for title, type, and description
            Label titleLabel = new Label("Title: " + p.getTitle_post());
            titleLabel.getStyleClass().add("title-label");
            Label typeLabel = new Label("Type: " + p.getType_post());
            typeLabel.getStyleClass().add("type-label");
            Label descriptionLabel = new Label("Description: " + p.getContenu_post());
            descriptionLabel.getStyleClass().add("description-label");
            Label likesLabel = new Label("Likes: " + sp.countLikesForPost(p.getId()));
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
            BorderPane.setAlignment(descriptionLabel, Pos.BOTTOM_LEFT);

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
                sp.Supprimer(p.getId()); // Replace deletePost with your actual method
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




    @FXML
    private void backToAllPosts() {
        postContainer.getChildren().clear();
        initialize(); // Reload all posts
    }

    @FXML
    private void showUserPostsButtonClicked() {
        // Call the method to display the user's posts
        showUserPosts();

    }
    @FXML
    private void showaddpost() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addpost.fxml"));
        Parent root = loader.load();
        postContainer.getScene().setRoot(root);

    }



}
