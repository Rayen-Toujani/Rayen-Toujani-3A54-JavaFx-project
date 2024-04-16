package com.visita.models;
import java.lang.*;

public class post {

    private int id_post , likes_post, id_creator;

    private String Title_post,Description_post,Type_post,Image_post;

    private String Dateposted , country;


    public post() {
    }

    public post(int id_post, int likes_post, int id_creator, String title_post, String description_post, String type_post, String image_post, String dateposted, String country) {
        this.id_post = id_post;
        this.likes_post = 0;
        this.id_creator = id_creator;
        this.Title_post = title_post;
        this.Description_post = description_post;
        this.Type_post = type_post;
        this.Image_post = image_post;
        this.Dateposted = dateposted;
        this.country = country;
    }

    public post(int likes_post, int id_creator, String title_post, String description_post, String type_post, String image_post, String dateposted, String country) {
        this.likes_post = 0;
        this.id_creator = id_creator;
        this.Title_post = title_post;
        this.Description_post = description_post;
        this.Type_post = type_post;
        this.Image_post = image_post;
        this.Dateposted = dateposted;
        this.country = country;
    }

    public post( String title_post,String type_post, String description_post, String image_post) {
        //this.likes_post = 0;
        //this.id_creator = id_creator;
        this.Title_post = title_post;
        this.Description_post = description_post;
        this.Type_post = type_post;
        this.Image_post = image_post;
       // this.Dateposted = dateposted;
        //this.country = country;
    }

    public post(int id_creator, String title_post, String description_post, String type_post, String image_post, String dateposted) {
        this.id_creator = id_creator;
        this.Title_post = title_post;
        this.Description_post = description_post;
        this.Type_post = type_post;
        this.Image_post = image_post;
        this.Dateposted = dateposted;
       // this.country = country;
    }


    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getLikes_post() {
        return likes_post;
    }

    public void setLikes_post(int likes_post) {
        this.likes_post = likes_post;
    }

    public int getId_creator() {
        return id_creator;
    }

    public void setId_creator(int id_creator) {
        this.id_creator = id_creator;
    }

    public String getTitle_post() {
        return Title_post;
    }

    public void setTitle_post(String title_post) {
        Title_post = title_post;
    }

    public String getDescription_post() {
        return Description_post;
    }

    public void setDescription_post(String description_post) {
        Description_post = description_post;
    }

    public String getType_post() {
        return Type_post;
    }

    public void setType_post(String type_post) {
        Type_post = type_post;
    }

    public String getImage_post() {
        return Image_post;
    }

    public void setImage_post(String image_post) {
        Image_post = image_post;
    }

    public String getDateposted() {
        return Dateposted;
    }

    public void setDateposted(String dateposted) {
        Dateposted = dateposted;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "post{" +
                "id_post=" + id_post +
                ", likes_post=" + likes_post +
                ", id_creator=" + id_creator +
                ", Title_post='" + Title_post + '\'' +
                ", Description_post='" + Description_post + '\'' +
                ", Type_post='" + Type_post + '\'' +
                ", Image_post='" + Image_post + '\'' +
                ", Dateposted='" + Dateposted + '\'' +
                ", country='" + country + '\'' +
                '}';
    }


}


