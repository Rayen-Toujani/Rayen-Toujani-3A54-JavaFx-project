package com.visita.controllers;

import com.visita.models.post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import com.visita.services.servicePost;

import java.util.List;

public class Backpost {

    @FXML
    private TableView<post> tableView;

    @FXML
    private TableColumn<post, String> titleColumn;

    @FXML
    private TableColumn<post, String> typeColumn;

    @FXML
    private TableColumn<post, String> descriptionColumn;

    @FXML
    private TableColumn<post, String> dateColumn;

    @FXML
    private TableColumn<post, String> countryColumn;
    @FXML
    private TableColumn<post, String> imageColumn;

    @FXML
    private TableColumn<post, Void> deleteColumn; // Column for the delete button

    private ObservableList<post> postList;

    servicePost sp = new servicePost();

    public void initialize() {

        servicePost sp = new servicePost();

        // Initialize the list of posts
        postList = FXCollections.observableArrayList();
        loadPosts(); // Load initial data into the list

        // Set the items of the TableView to the list of posts
        tableView.setItems(postList);

        // Map columns to properties of the post class
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title_post"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type_post"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("contenu_post"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("makedate_post"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image_post"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));





        // Add delete button to each row
        deleteColumn.setCellFactory(column -> new TableCell<post, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    post currentPost = getTableView().getItems().get(getIndex());
                    deletePost(currentPost);
                    //System.out.println(currentPost.getId_post());


                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }



    // Load initial posts data (you can replace this with real data fetching logic)
    private void loadPosts() {


        List<post> posts = sp.afficher();


        for (post p : posts){
        postList.add(new post(p.getId(), p.getLikes_post(), p.getId_creator(), p.getTitle_post(), p.getContenu_post(), p.getType_post(), p.getImage_post(), p.getMakedate_post(),p.getCountry()));
        // Add more posts as needed

            }
    }

    // Handle deletion of a post
    private void deletePost(post postToDelete) {
        sp.Supprimer(postToDelete.getId_post());
        postList.remove(postToDelete);

        //System.out.println(postToDelete.getId() + postToDelete.getType_post());
        // Add code here to handle additional delete logic, such as removing the post from the database
    }
}
