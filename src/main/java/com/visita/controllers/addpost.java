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
    private TextArea Post_Description;
    @FXML
    private ImageView uploadedImageView;

    @FXML
    private ChoiceBox<String> Typepostchoice;

    private String imagePath;
    private String typepost;

    private final servicePost ps = new servicePost();


    @FXML
    public void initialize(){
        ObservableList<String> items = FXCollections.observableArrayList("Medicine","Heart","Free counselling","Lab test","Equipments");
        Typepostchoice.setItems(items);
        Typepostchoice.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> typepost=newValue));


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





    @FXML
    void ajouterPost(ActionEvent event) {
        try {
            if (validateInput()) {


                ps.ajouter(new post(777, Post_Title.getText(), Post_Description.getText(), typepost, imagePath,"aaa"));

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
