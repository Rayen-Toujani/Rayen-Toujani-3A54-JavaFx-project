package com.visita.models;

public class LikesPost {
    private int id_like	;
    private int id_post	;
    private int id_user;

    public LikesPost() {
    }

    public LikesPost(int id_like, int id_post, int id_user) {
        this.id_like = id_like;
        this.id_post = id_post;
        this.id_user = id_user;
    }

    public int getId_like() {
        return id_like;
    }

    public void setId_like(int id_like) {
        this.id_like = id_like;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
