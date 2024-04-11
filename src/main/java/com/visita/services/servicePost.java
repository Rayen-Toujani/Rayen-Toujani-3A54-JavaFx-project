package com.visita.services;

import com.visita.models.post;
import com.visita.utils.DataSource;

import java.sql.*;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.util.stream.Collectors;

public class servicePost {

private     Connection cnx = DataSource.getInstance().getConnection();

    public boolean ajouter(post t) {
        boolean a = false;
        try {
            // Get the current country
            String country = getCurrentCountry();

            String req = "INSERT INTO post (Id_creator, Title_post, Type_post, Description_post, Image_post, Dateposted, Country, likes_post) VALUES (?, ?, ?, ?, ?, CURDATE(), ?, 0)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_creator());
            ps.setString(2, t.getTitle_post());
            ps.setString(3, t.getType_post());
            ps.setString(4, t.getDescription_post());
            ps.setString(5, t.getImage_post());
            ps.setString(6, country);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Post ajoutée");
                a = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error fetching current country: " + ex.getMessage());
        }
        return a;
    }
    private String getCurrentCountry() throws IOException {
        // Construct the URL with your token as a query parameter
        String url = "https://ipinfo.io/json?token=0017357e92579e";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        // Read the response from the API
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            // Convert the response to a String
            String response = in.lines().collect(Collectors.joining());
            // Parse the JSON response to extract the country
            // Assuming the JSON response contains a "country" field
            String country = parseCountryFromJson(response);
            return country;
        }
    }

    private String parseCountryFromJson(String json) {
        // Parse the JSON string to extract the country information
        // Implement parsing logic based on the structure of the JSON response
        // For ipinfo.io, the country information is available in the "country" field
        // Example parsing logic:
        if (json.contains("\"country\"")) {
            // Find the index of the "country" field
            int countryIndex = json.indexOf("\"country\"");
            // Find the index of the first occurrence of the country value after the "country" field
            int countryValueIndex = json.indexOf("\"", countryIndex + 10);
            // Find the index of the closing quote of the country value
            int closingQuoteIndex = json.indexOf("\"", countryValueIndex + 1);
            // Extract the country value substring
            String countryValue = json.substring(countryValueIndex + 1, closingQuoteIndex);
            // Check if the country value contains a comma
            if (countryValue.contains(",")) {
                // If the country value contains a comma, split it and return the first country
                return countryValue.split(",")[0].trim();
            } else {
                // If the country value doesn't contain a comma, return it as is
                return countryValue;
            }
        } else {
            // If the "country" field is not found in the JSON response, return "Unknown"
            return "Unknown";
        }
    }
    public void modifier(post t) {
        try {
            String req = "UPDATE post SET Title_post=?, Type_post=?, Description_post=?, Image_post=? WHERE id_post=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getTitle_post());
            ps.setString(2, t.getType_post());
            ps.setString(3, t.getDescription_post());
            ps.setString(4, t.getImage_post());
            ps.setInt(5, t.getId_post()); // Assuming id_post is of type int in the database
            ps.executeUpdate();
            System.out.println("Post modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    public void Supprimer(int id) {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM post WHERE id_post = "+id+"";
            st.executeUpdate(req);
            System.out.println("post supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public List<post> afficher() {
        List<post> posts = new ArrayList<>();
        try {
            String req = "select * from post ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                post p = new post();
                p.setId_post(rs.getInt(1));
                p.setDescription_post(rs.getString("Description_post"));
                p.setTitle_post(rs.getString("Title_post"));
                p.setType_post(rs.getString("Type_post"));
                p.setDateposted(rs.getString("Dateposted"));
                p.setImage_post(rs.getString("Image_post"));
                posts.add(p);
            }
            System.out.print(posts);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }

}
