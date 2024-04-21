package com.visita.services;

import com.visita.models.post;
import com.visita.utils.DataSource;
import  com.visita.utils.*;
import com.visita.models.comment;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.*;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

public class serviceComment {


    private Connection cnx = DataSource.getInstance().getConnection();

    public boolean ajouter(comment t) {
        boolean a = false;
        try {


            String req = "INSERT INTO comment (id_comment,name_comment,mail_comment,id_creatorcom,id_post_id,datecreation_comment,contenu_comment) VALUES (1,'name','mail',?, ?,CURDATE(), ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_creatorcom());
            ps.setInt(2, t.getId_post_id());
            ps.setString(3, t.getContenu_comment());


            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("comment ajoutée");
                a = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return a;
    }

    public void modifier(comment com) {
        try {

            String req = "update comment set contenu_comment=? where id= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, com.getContenu_comment());
            ps.setInt(2, (int) com.getId());
            ps.executeUpdate();
            System.out.println("Comment modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void Supprimer(int id) {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM comment WHERE id = "+id+"";
            st.executeUpdate(req);
            System.out.println("Comment supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<comment> afficher(int idp) {
        List<comment> comments = new ArrayList<>();
        try {
            String req = "select * from comment ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                comment p = new comment();
                //p.setid(rs.getInt(1));

                p.setContenu_comment(rs.getString("comment"));
                p.setDatecreation_comment(rs.getString("datecreation_comment"));
                p.setId_post_id(idp);
                comments.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }

    public List<comment> affichersingle(int idp) {
        List<comment> comments = new ArrayList<>();
        try {
            String req = "SELECT * FROM comment WHERE id_post_id = " + idp;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                comment p = new comment();
                //p.setid(rs.getInt(1));

                p.setContenu_comment(rs.getString("contenu_comment"));
                p.setDatecreation_comment(rs.getString("datecreation_comment"));
                p.setId_post_id(idp);
                comments.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }


}
