package com.visita.services;

import com.visita.models.post;
import com.visita.models.LikesPost;
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

            String req = "INSERT INTO post (id_post,Id_creator, title_post, type_post, contenu_post, Image_post, makedate_post, Country, likes_post,phonenumber,validation_post) VALUES (1,?, ?, ?, ?, ?, CURDATE(), ?, 0,?,0)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_creator());
            ps.setString(2, t.getTitle_post());
            ps.setString(3, t.getType_post());
            ps.setString(4, t.getContenu_post());
            ps.setString(5, t.getImage_post());
            ps.setString(6, country);
            ps.setInt(7, t.getPhonenumber());

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

    public List<post> getPostsByUser(int userId) {
        List<post> userPosts = new ArrayList<>();

        servicePost ps = new servicePost();

        // Assuming you have a list of all posts in your application
        List<post> allPosts = ps.afficher(); // Replace sp.afficher() with your actual method to get all posts

        // Iterate through all posts and filter those belonging to the specified user
        for (post p : allPosts) {
            if (p.getId_creator() == userId) { // Replace getUserId() with the method to get the user ID associated with the post
                userPosts.add(p);
            }
        }

        return userPosts;
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
            String req = "UPDATE post SET title_post=?, type_post=?, contenu_post=?, Image_post=? WHERE id=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getTitle_post());
            ps.setString(2, t.getType_post());
            ps.setString(3, t.getContenu_post());
            ps.setString(4, t.getImage_post());
            ps.setInt(5, t.getId()); // Assuming id_post is of type int in the database
            ps.executeUpdate();
            System.out.println("Post modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    public void Supprimer(int id_post) {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM post WHERE id = "+id_post+"";
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
                p.setId(rs.getInt(1));
                p.setId_post(rs.getInt("id_post"));
                p.setContenu_post(rs.getString("contenu_post"));
                p.setTitle_post(rs.getString("title_post"));
                p.setType_post(rs.getString("type_post"));
                p.setMakedate_post(rs.getString("makedate_post"));
                p.setImage_post(rs.getString("image_post"));
                p.setLikes_post(rs.getInt("likes_post"));
                p.setId_creator(rs.getInt("id_creator"));
                p.setCountry(rs.getString("country"));
                p.setValidation_post(rs.getInt("validation_post"));
                posts.add(p);
            }
            System.out.print(posts);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }

    public List<post> afficheruserpost(int idp) {
        List<post> posts = new ArrayList<>();
        try {
            String req = "select * from post WHERE id_creator ="+idp;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                post p = new post();
                p.setId(rs.getInt(1));
                p.setId_post(rs.getInt("id_post"));
                p.setContenu_post(rs.getString("contenu_post"));
                p.setTitle_post(rs.getString("title_post"));
                p.setType_post(rs.getString("type_post"));
                p.setMakedate_post(rs.getString("makedate_post"));
                p.setImage_post(rs.getString("image_post"));
                p.setLikes_post(rs.getInt("likes_post"));
                p.setId_creator(rs.getInt("id_creator"));
                p.setCountry(rs.getString("country"));
                p.setValidation_post(rs.getInt("validate_post"));
                posts.add(p);
            }
            System.out.print(posts);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return posts;
    }


    public boolean addLike(int postId, int userId) {
        boolean success = false;
        try {
            String query = "INSERT INTO likes_post (id_post, id_user) VALUES (?, ?)";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, postId);
            statement.setInt(2, userId);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
                System.out.println("Like added successfully.");
            }
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
        return success;
    }


    public boolean removeLike(int postId, int userId) {
        boolean success = false;
        try {
            String query = "DELETE FROM likes_post WHERE id_post = ? AND id_user = ?";
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setInt(1, postId);
            statement.setInt(2, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
                System.out.println("Like removed successfully.");
            }
        } catch (SQLException ex) {
            System.out.println("Error removing like: " + ex.getMessage());
        }
        return success;
    }


    // Inside servicePost class

    public boolean hasLikedPost(int postId, int userId) {
        // Implement logic to check if the user has already liked the post
        // Query the database or any other storage mechanism to check if the like exists
        // Return true if the user has liked the post, false otherwise
        // Example:
        try (PreparedStatement ps = cnx.prepareStatement("SELECT COUNT(*) FROM likes_post WHERE id_post = ? AND id_user = ?")) {
            ps.setInt(1, postId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error checking if the user has liked the post: " + ex.getMessage());
        }
        return false;
    }


    public int countTotalPosts() {
        int totalCount = 0;
        try (PreparedStatement ps = cnx.prepareStatement("SELECT COUNT(*) FROM post");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error counting total posts: " + ex.getMessage());
        }
        return totalCount;
    }

    public int countTotalPostsuser(int user_id) {
        int totalCount = 0;
        String query = "SELECT COUNT(*) FROM post WHERE id_user = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            // Set the user_id parameter
            ps.setInt(1, user_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error counting total posts: " + ex.getMessage());
            // Consider logging or rethrowing the exception based on your needs
        }

        return totalCount;
    }


    public List<post> getValidatedPostsForPage(int offset, int pageSize) {
        List<post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post WHERE validation_post = 1 LIMIT ? OFFSET ?")) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    posts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching validated posts for page: " + ex.getMessage());
        }
        return posts;
    }

    public List<post> getValidatedUserPosts(int userId) {
        List<post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post WHERE id_creator = ? AND validation_post = 1")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    posts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching validated user posts: " + ex.getMessage());
        }
        return posts;
    }


    private post populatePostFromResultSet(ResultSet rs) throws SQLException {
        post p = new post();
        p.setId(rs.getInt(1));
        p.setId_post(rs.getInt("id_post"));
        p.setContenu_post(rs.getString("contenu_post"));
        p.setTitle_post(rs.getString("title_post"));
        p.setType_post(rs.getString("type_post"));
        p.setMakedate_post(rs.getString("makedate_post"));
        p.setImage_post(rs.getString("image_post"));
        p.setLikes_post(rs.getInt("likes_post"));
        p.setId_creator(rs.getInt("id_creator"));
        p.setCountry(rs.getString("country"));
        p.setValidation_post(rs.getInt("validation_post"));
        // Populate other attributes as needed
        return p;
    }

    public List<post> getPostsForPage(int offset, int pageSize) {
        List<post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post LIMIT ? OFFSET ?")) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    posts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching posts for page: " + ex.getMessage());
        }
        return posts;
    }


    public List<post> getPaginatedUserPosts(int offset, int pageSize, int id_user) {
        List<post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post WHERE id_user = ? LIMIT ?, ?")) {
            // Set parameters in the order they appear in the query
            ps.setInt(1, id_user);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    posts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching posts for page: " + ex.getMessage());
        }
        return posts;
    }



    public void incrementLikes(int postId) {
        try {
            String query = "UPDATE post SET likes_post = likes_post + 1 WHERE id_post = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, postId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Likes incremented for post with ID: " + postId);
            } else {
                System.out.println("Failed to increment likes for post with ID: " + postId);
            }
        } catch (SQLException ex) {
            System.out.println("Error incrementing likes: " + ex.getMessage());
        }
    }

    public void decrementLikes(int postId) {
        try {
            String query = "UPDATE post SET likes_post = likes_post - 1 WHERE id_post = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, postId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Likes decremented for post with ID: " + postId);
            } else {
                System.out.println("Failed to decrement likes for post with ID: " + postId);
            }
        } catch (SQLException ex) {
            System.out.println("Error decrementing likes: " + ex.getMessage());
        }
    }

    public int countLikesForPost(int postId) {
        int likeCount = 0;
        try {
            String query = "SELECT COUNT(*) AS like_count FROM likes_post WHERE id_post = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                likeCount = rs.getInt("like_count");
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error counting likes for post with ID " + postId + ": " + ex.getMessage());
        }
        return likeCount;
    }


    public List<post> getPostsOrderByLikes(int offset, int pageSize) {
        List<post> orderedPosts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post ORDER BY likes_post DESC LIMIT ? OFFSET ?")) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    orderedPosts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching posts ordered by likes: " + ex.getMessage());
        }
        return orderedPosts;
    }


    public List<post> getPostsOrderByDateDescending(int offset, int pageSize) {
        List<post> orderedPosts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post ORDER BY makedate_post DESC LIMIT ? OFFSET ?")) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    orderedPosts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching posts ordered by date descending: " + ex.getMessage());
        }
        return orderedPosts;
    }


    public List<post> getPostsOrderByDateAscending(int offset, int pageSize) {
        List<post> orderedPosts = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement("SELECT * FROM post ORDER BY makedate_post ASC LIMIT ? OFFSET ?")) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = populatePostFromResultSet(rs);
                    orderedPosts.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching posts ordered by date descending: " + ex.getMessage());
        }
        return orderedPosts;
    }


    public List<post> searchPosts(String keyword) {
        // Initialize an empty list to hold the matching posts
        List<post> matchingPosts = new ArrayList<>();

        // Create the SQL query to search for posts by keyword in title and description
        String sqlQuery = "SELECT * FROM post WHERE LOWER(title_post) LIKE ? OR LOWER(contenu_post) LIKE ?";

        try (PreparedStatement ps = cnx.prepareStatement(sqlQuery)) {
            // Set the parameters for the prepared statement
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            // Execute the query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create post objects
            while (rs.next()) {
                // Create a new post object from the current row in the result set
                post p = new post();
                p.setId(rs.getInt(1));
                p.setId_post(rs.getInt("id_post"));
                p.setContenu_post(rs.getString("contenu_post"));
                p.setTitle_post(rs.getString("title_post"));
                p.setType_post(rs.getString("type_post"));
                p.setMakedate_post(rs.getString("makedate_post"));
                p.setImage_post(rs.getString("image_post"));
                p.setLikes_post(rs.getInt("likes_post"));
                p.setId_creator(rs.getInt("id_creator"));
                p.setCountry(rs.getString("country"));

                // Add the post to the list of matching posts
                matchingPosts.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Error searching for posts: " + ex.getMessage());
        }

        // Return the list of matching posts
        return matchingPosts;
    }

    public List<post> searchAndFilterPosts(String keyword, String startDate, String endDate, String user, String location, String sortBy) {
        List<post> filteredPosts = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM post WHERE validation_post = 1");

        // Add keyword filter
        if (keyword != null && !keyword.isEmpty()) {
            sqlQuery.append(" AND (LOWER(title_post) LIKE ? OR LOWER(contenu_post) LIKE ?)");
        }

        // Add date range filter
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            sqlQuery.append(" AND makedate_post BETWEEN ? AND ?");
        }

        // Add user filter
        if (user != null && !user.isEmpty()) {
            sqlQuery.append(" AND LOWER(id_creator) = ?");
        }

        // Add location filter
        if (location != null && !location.isEmpty()) {
            sqlQuery.append(" AND LOWER(country) = ?");
        }

        // Add sorting criteria
        switch (sortBy != null ? sortBy.toLowerCase() : "") {
            case "date":
                sqlQuery.append(" ORDER BY makedate_post DESC");
                break;
            case "likes":
                sqlQuery.append(" ORDER BY likes_post DESC");
                break;
            case "title":
                sqlQuery.append(" ORDER BY title_post ASC");
                break;
            default:
                sqlQuery.append(" ORDER BY makedate_post DESC");
                break;
        }

        // Use a PreparedStatement for the query
        try (PreparedStatement ps = cnx.prepareStatement(sqlQuery.toString())) {
            // Set the parameters based on the input
            int parameterIndex = 1;

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(parameterIndex++, "%" + keyword.toLowerCase() + "%");
                ps.setString(parameterIndex++, "%" + keyword.toLowerCase() + "%");
            }

            if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                ps.setDate(parameterIndex++, Date.valueOf(startDate));
                ps.setDate(parameterIndex++, Date.valueOf(endDate));
            }

            if (user != null && !user.isEmpty()) {
                ps.setString(parameterIndex++, user.toLowerCase());
            }

            if (location != null && !location.isEmpty()) {
                ps.setString(parameterIndex++, location.toLowerCase());
            }

            // Execute the query and process the results
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    post p = new post();
                    // Populate the post object from the result set
                    p.setId(rs.getInt(1));
                    p.setId_post(rs.getInt("id_post"));
                    p.setContenu_post(rs.getString("contenu_post"));
                    p.setTitle_post(rs.getString("title_post"));
                    p.setType_post(rs.getString("type_post"));
                    p.setMakedate_post(rs.getString("makedate_post"));
                    p.setImage_post(rs.getString("image_post"));
                    p.setLikes_post(rs.getInt("likes_post"));
                    p.setId_creator(rs.getInt("id_creator"));
                    p.setCountry(rs.getString("country"));
                    // Add the post to the list
                    filteredPosts.add(p);
                }
            }
        } catch (SQLException ex) {
            // Properly handle or log SQL exceptions
            System.out.println("Error searching and filtering posts: " + ex.getMessage());
        }

        return filteredPosts;
    }




    public void validatePost(int id_post) {
        // Define the query to update the validation_post column
        String query = "UPDATE post SET validation_post = 1 WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            // Set the ID of the post to validate
            ps.setInt(1, id_post);

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            // Check if any rows were affected
            if (rowsAffected > 0) {
                System.out.println("Successfully validated post with ID: " + id_post);
            } else {
                System.out.println("No post found with ID: " + id_post);
            }
        } catch (SQLException ex) {
            System.out.println("Error validating post: " + ex.getMessage());
        }
    }




}
