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


            String req = "INSERT INTO comment (id_creatorcom,id_post,creationdate,comment) VALUES (?, ?,CURDATE(), ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_creatorcom());
            ps.setInt(2, t.getId_post());
            ps.setString(3, t.getComment());


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

            String req = "update comment set comment=? where id_comment= ?";
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, com.getComment());
            ps.setInt(2, (int) com.getId_comment());
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
            String req = "DELETE FROM comment WHERE id_comment = "+id+"";
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
                //p.setId_comment(rs.getInt(1));

                p.setComment(rs.getString("comment"));
                p.setCreationdate(rs.getString("creationdate"));
                p.setId_post(idp);
                comments.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return comments;
    }


}
