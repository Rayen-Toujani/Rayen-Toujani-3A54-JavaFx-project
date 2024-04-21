package com.visita.models;
import java.lang.*;

public class post {

    private int id_post;
    private int likes_post;
    private int id_creator;



    private int id;

    private String title_post,contenu_post,type_post,Image_post;

    private String makedate_post , country;


    public post() {
    }

    public post(int id_post, int likes_post, int id_creator, String title_post, String contenu_post , String type_post, String image_post, String makedate_post, String country) {
        this.id_post = id_post;
        this.likes_post = 0;
        this.id_creator = id_creator;
        this.title_post = title_post;
        this.contenu_post  = contenu_post ;
        this.type_post = type_post;
        this.Image_post = image_post;
        this.makedate_post = makedate_post;
        this.country = country;
    }

    public post(int likes_post, int id_creator, String title_post, String contenu_post , String type_post, String image_post, String makedate_post, String country) {
        this.likes_post = 0;
        this.id_creator = id_creator;
        this.title_post = title_post;
        this.contenu_post  = contenu_post ;
        this.type_post = type_post;
        this.Image_post = image_post;
        this.makedate_post = makedate_post;
        this.country = country;
    }

    public post( String title_post,String type_post, String contenu_post , String image_post) {
        //this.likes_post = 0;
        //this.id_creator = id_creator;
        this.title_post = title_post;
        this.contenu_post  = contenu_post ;
        this.type_post = type_post;
        this.Image_post = image_post;
       // this.makedate_post = makedate_post;
        //this.country = country;
    }

    public post(int id_creator, String title_post, String contenu_post , String type_post, String image_post, String makedate_post) {
        this.id_creator = id_creator;
        this.title_post = title_post;
        this.contenu_post  = contenu_post ;
        this.type_post = type_post;
        this.Image_post = image_post;
        this.makedate_post = makedate_post;
       // this.country = country;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return title_post;
    }

    public void setTitle_post(String title_post) {
        this.title_post = title_post;
    }

    public String getContenu_post () {
        return contenu_post ;
    }

    public void setContenu_post (String contenu_post ) {
        this.contenu_post  = contenu_post ;
    }

    public String getType_post() {
        return type_post;
    }

    public void setType_post(String type_post) {
        this.type_post = type_post;
    }

    public String getImage_post() {
        return Image_post;
    }

    public void setImage_post(String image_post) {
        Image_post = image_post;
    }

    public String getMakedate_post() {
        return makedate_post;
    }

    public void setMakedate_post(String makedate_post) {
        this.makedate_post = makedate_post;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "post{" +"id= " + id +
                ", id_post=" + id_post +
                ", likes_post=" + likes_post +
                ", id_creator=" + id_creator +
                ", title_post='" + title_post + '\'' +
                ", contenu_post ='" + contenu_post  + '\'' +
                ", type_post='" + type_post + '\'' +
                ", Image_post='" + Image_post + '\'' +
                ", makedate_post='" + makedate_post + '\'' +
                ", country='" + country + '\'' +
                '}';
    }


}


