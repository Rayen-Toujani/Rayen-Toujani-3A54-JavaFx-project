package com.visita.models;

public class comment {

    private int id_comment ,id_creatorcom,id_post;
    private String creationdate , comment;


    public comment() {
    }

    public comment(int id_comment, int id_creatorcom, int id_post, String creationdate, String comment) {
        this.id_comment = id_comment;
        this.id_creatorcom = id_creatorcom;
        this.id_post = id_post;
        this.creationdate = creationdate;
        this.comment = comment;
    }

    public comment(int id_creatorcom, int id_post, String creationdate, String comment) {
        this.id_creatorcom = id_creatorcom;
        this.id_post = id_post;
        this.creationdate = creationdate;
        this.comment = comment;
    }

    public comment(String comment) {
        this.comment = comment;
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public int getId_creatorcom() {
        return id_creatorcom;
    }

    public void setId_creatorcom(int id_creatorcom) {
        this.id_creatorcom = id_creatorcom;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "comment{" +
                "id_comment=" + id_comment +
                ", id_creatorcom=" + id_creatorcom +
                ", id_post=" + id_post +
                ", creationdate='" + creationdate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
