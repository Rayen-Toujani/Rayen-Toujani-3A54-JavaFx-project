package com.visita.test;


import com.visita.models.comment;
import com.visita.models.post;
import com.visita.services.servicePost;
import com.visita.services.serviceComment;
import com.visita.utils.DataSource;

import javax.xml.stream.events.Comment;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) {



        servicePost ps = new servicePost();
       // post newPost = new post(777, 777, "zezae", "azerz", "azer","aaa","qsdfqsdf","sdfqsdf");
        //newPost.setImage_post("rrrrrr"); Assuming setImage_post method exists in your post class
       // ps.Supprimer(55);
        //post  posttomodify = new post();
        //posttomodify.setId_post(66);
        //posttomodify.setTitle_post("New Title");
        //posttomodify.setType_post("New Type");
        //posttomodify.setDescription_post("New Description");
        //posttomodify.setImage_post("New Image");
        //ps.modifier(posttomodify);
        //ps.afficher();

        serviceComment cs = new serviceComment();
        System.out.println(cs.afficherall());
        //comment newComment = new comment(1,77,"azea","nicce post");
        //cs.ajouter(newComment);
        //cs.Supprimer(134);


    }
    }




