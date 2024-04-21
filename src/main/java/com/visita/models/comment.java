package com.visita.models;

public class comment {

    private int id ,id_creatorcom,id_post_id;
    private String datecreation_comment , contenu_comment;


    public comment() {
    }

    public comment(int id, int id_creatorcom, int id_post_id, String datecreation_comment, String contenu_comment) {
        this.id = id;
        this.id_creatorcom = id_creatorcom;
        this.id_post_id = id_post_id;
        this.datecreation_comment = datecreation_comment;
        this.contenu_comment = contenu_comment;
    }

    public comment(int id_creatorcom, int id_post_id, String datecreation_comment, String contenu_comment) {
        this.id_creatorcom = id_creatorcom;
        this.id_post_id = id_post_id;
        this.datecreation_comment = datecreation_comment;
        this.contenu_comment = contenu_comment;
    }


    public comment(String contenu_comment) {
        this.contenu_comment = contenu_comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_creatorcom() {
        return id_creatorcom;
    }

    public void setId_creatorcom(int id_creatorcom) {
        this.id_creatorcom = id_creatorcom;
    }

    public int getId_post_id() {
        return id_post_id;
    }

    public void setId_post_id(int id_post_id) {
        this.id_post_id = id_post_id;
    }

    public String getDatecreation_comment() {
        return datecreation_comment;
    }

    public void setDatecreation_comment(String datecreation_comment) {
        this.datecreation_comment = datecreation_comment;
    }

    public String getContenu_comment() {
        return contenu_comment;
    }

    public void setContenu_comment(String contenu_comment) {
        this.contenu_comment = contenu_comment;
    }

    @Override
    public String toString() {
        return "comment{" +
                "id=" + id +
                ", id_creatorcom=" + id_creatorcom +
                ", id_post_id=" + id_post_id +
                ", datecreation_comment='" + datecreation_comment + '\'' +
                ", contenu_comment='" + contenu_comment + '\'' +
                '}';
    }
}
