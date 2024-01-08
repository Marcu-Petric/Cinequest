package com.example.proiect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.sql.*;
import java.io.IOException;
import java.time.LocalDate;


public class DBUtils {

    private static Boolean goBackFromReview;

    private static Review choosenReview;

    private static List choosenList;

    public static List getChoosenList() {
        return choosenList;
    }

    public static void setChoosenList(List choosenList) {
        DBUtils.choosenList = choosenList;
    }

    public static Review getChoosenReview() {
        return choosenReview;
    }

    public static void setChoosenReview(Review choosenReview) {
        DBUtils.choosenReview = choosenReview;
    }

    private static String username;

    private static Integer movie;

    public static String getMovieTitle() {
        return movieTitle;
    }

    public static void setMovieTitle(String movieTitle) {
        DBUtils.movieTitle = movieTitle;
    }

    private static String movieTitle;

    private static  Integer userID;


    public static Integer getUserID() {
        return userID;
    }

    public static void setUserID(Integer userID) {
        DBUtils.userID = userID;
    }

    private static Integer list;

    private static Integer watchedByNumber;

    private static Float ratingOfTheMovie;

    public static Integer getWatchedByNumber() {
        return watchedByNumber;
    }

    public static void setWatchedByNumber(Integer watchedByNumber) {
        DBUtils.watchedByNumber = watchedByNumber;
    }

    public static Float getRatingOfTheMovie() {
        return ratingOfTheMovie;
    }

    public static void setRatingOfTheMovie(Float ratingOfTheMovie) {
        DBUtils.ratingOfTheMovie = ratingOfTheMovie;
    }

    public static Integer getMovie() {
        return movie;
    }

    public static void setMovie(Integer movie) {
        DBUtils.movie = movie;
    }

    public static Integer getList() {
        return list;
    }

    public static void setList(Integer list) {
        DBUtils.list = list;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DBUtils.username = username;
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Stage stage = new Stage();
        stage.setTitle(title);
        if (fxmlFile.equals("singup.fxml") || fxmlFile.equals("reviewSee.fxml") ||fxmlFile.equals("review.fxml")) {
            assert root != null;
            stage.setScene(new Scene(root, 600, 400));
        }
        else {
            assert root != null;
            stage.setScene(new Scene(root, screenBounds.getWidth(), screenBounds.getHeight()));
        }

        // Show the stage
        stage.show();

        // Close the previous stage
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }


    public static void signUpUser(ActionEvent event, String username, String password, String email) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM \"user\" WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is already taken!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO \"user\" (username, password, email) VALUES(?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, email);
                psInsert.executeUpdate();

                changeScene(event, "login.fxml", "You shall not pass");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(psCheckUserExists);
            DBUtils.closeStatement(psInsert);
            DBUtils.closeConnection(connection);
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT \"userID\", password FROM \"user\" WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided username was not found");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    Integer retrievedID = resultSet.getInt("userID");
                    if (retrievedPassword.equals(password)) {
                        DBUtils.setUserID(retrievedID);
                        DBUtils.setUsername(username);
                        changeScene(event, "mainpg.fxml", "Here\'s Johnny!");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The credentials are incorrect!");
                        alert.show();

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }
    public static ObservableList<Review> searchForMyReviews(Integer x){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findUsername = null;
        PreparedStatement findRating = null;
        ResultSet ratingSet = null;
        ResultSet resultSet = null;
        ResultSet usernameSet = null;
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            if (x == 0) {
                preparedStatement = connection.prepareStatement("SELECT\n" +
                        "    r.*,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) AS like_count,\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS dislike_count,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) -\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS popularity\n" +
                        "FROM\n" +
                        "    review r\n" +
                        "        LEFT JOIN\n" +
                        "    liked_review lr ON r.review_id = lr.\"reviewID\"\n" +
                        "WHERE user_id = ?\n" +
                        "GROUP BY\n" +
                        "    r.review_id");
                preparedStatement.setInt(1, DBUtils.getUserID());
            }
            else if (x == 1){
                preparedStatement = connection.prepareStatement("SELECT\n" +
                        "    r.*,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) AS like_count,\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS dislike_count,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) -\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS popularity\n" +
                        "FROM\n" +
                        "    review r\n" +
                        "        LEFT JOIN\n" +
                        "    liked_review lr ON r.review_id = lr.\"reviewID\"\n" +
                        "WHERE user_id = ?\n" +
                        "GROUP BY\n" +
                        "    r.review_id\n" +
                        "ORDER BY\n" +
                        "    popularity DESC;");
                preparedStatement.setInt(1, DBUtils.getUserID());
            }
            else {
                preparedStatement = connection.prepareStatement("SELECT\n" +
                        "    r.*,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) AS like_count,\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS dislike_count,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) -\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS popularity\n" +
                        "FROM\n" +
                        "    review r\n" +
                        "        LEFT JOIN\n" +
                        "    liked_review lr ON r.review_id = lr.\"reviewID\"\n" +
                        "WHERE user_id = ?\n" +
                        "GROUP BY\n" +
                        "    r.review_id\n" +
                        "ORDER BY\n" +
                        "    popularity ASC;");
                preparedStatement.setInt(1, DBUtils.getUserID());
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer likesCount = resultSet.getInt("like_count");
                Integer movieID = resultSet.getInt("movie_id");
                Integer dislikesCount = resultSet.getInt("dislike_count");
                String thoughts = resultSet.getString("review_text");
                Integer reviewID = resultSet.getInt("review_id");
                String movieTitle = null;
                findUsername = connection.prepareStatement("SELECT \"Title\" FROM movie WHERE movie.id = ?");
                findUsername.setInt(1, movieID);
                usernameSet = findUsername.executeQuery();
                while (usernameSet.next()){
                    movieTitle = usernameSet.getString("Title");
                }
                findRating = connection.prepareStatement("SELECT rating FROM ratings WHERE user_id = ? and movie_id = ?");
                findRating.setInt(1, DBUtils.getUserID());
                findRating.setInt(2, movieID);
                ratingSet = findRating.executeQuery();
                Integer ratingVal = null;
                while(ratingSet.next()){
                    ratingVal = ratingSet.getInt("rating");
                }
                Review review = new Review(reviewID, ratingVal, movieTitle, thoughts, likesCount, dislikesCount);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findUsername);
            DBUtils.closeStatement(findRating);
            DBUtils.closeResult(usernameSet);
            DBUtils.closeResult(ratingSet);
            DBUtils.closeConnection(connection);
        }

        return reviews;
    }
    public static ObservableList<Review> searchForReviews(Integer x){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findUsername = null;
        PreparedStatement findRating = null;
        ResultSet ratingSet = null;
        ResultSet resultSet = null;
        ResultSet usernameSet = null;
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            if (x == 0) {
                preparedStatement = connection.prepareStatement("SELECT\n" +
                        "    r.*,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) AS like_count,\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS dislike_count,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) -\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS popularity\n" +
                        "FROM\n" +
                        "    review r\n" +
                        "        LEFT JOIN\n" +
                        "    liked_review lr ON r.review_id = lr.\"reviewID\"\n" +
                        "WHERE movie_id = ?\n" +
                        "GROUP BY\n" +
                        "    r.review_id");
                preparedStatement.setInt(1, DBUtils.getMovie());
            }
            else if (x == 1){
                preparedStatement = connection.prepareStatement("SELECT\n" +
                        "    r.*,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) AS like_count,\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS dislike_count,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) -\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS popularity\n" +
                        "FROM\n" +
                        "    review r\n" +
                        "        LEFT JOIN\n" +
                        "    liked_review lr ON r.review_id = lr.\"reviewID\"\n" +
                        "WHERE movie_id = ?\n" +
                        "GROUP BY\n" +
                        "    r.review_id\n" +
                        "ORDER BY\n" +
                        "    popularity DESC;");
                preparedStatement.setInt(1, DBUtils.getMovie());
            }
            else {
                preparedStatement = connection.prepareStatement("SELECT\n" +
                        "    r.*,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) AS like_count,\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS dislike_count,\n" +
                        "    SUM(CASE WHEN lr.liked = true THEN 1 ELSE 0 END) -\n" +
                        "    SUM(CASE WHEN lr.liked = false THEN 1 ELSE 0 END) AS popularity\n" +
                        "FROM\n" +
                        "    review r\n" +
                        "        LEFT JOIN\n" +
                        "    liked_review lr ON r.review_id = lr.\"reviewID\"\n" +
                        "WHERE movie_id = ?\n" +
                        "GROUP BY\n" +
                        "    r.review_id\n" +
                        "ORDER BY\n" +
                        "    popularity ASC;");
                preparedStatement.setInt(1, DBUtils.getMovie());
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer likesCount = resultSet.getInt("like_count");
                Integer dislikesCount = resultSet.getInt("dislike_count");
                Integer userReviewID = resultSet.getInt("user_id");
                String thoughts = resultSet.getString("review_text");
                String actual_review = thoughts;
                Boolean spoilers = resultSet.getBoolean("spoiler");
                Integer reviewID = resultSet.getInt("review_id");
                String userReview = null;
                findUsername = connection.prepareStatement("SELECT username FROM \"user\" where \"userID\" = ?");
                findUsername.setInt(1, userReviewID);
                usernameSet = findUsername.executeQuery();
                while (usernameSet.next()){
                    userReview = usernameSet.getString("username");
                }
                findRating = connection.prepareStatement("SELECT rating FROM ratings WHERE user_id = ? and movie_id = ?");
                findRating.setInt(1, userReviewID);
                findRating.setInt(2, DBUtils.getMovie());
                ratingSet = findRating.executeQuery();
                Integer ratingVal = null;
                while(ratingSet.next()){
                    ratingVal = ratingSet.getInt("rating");
                }
                if (spoilers == true)
                    thoughts = "THIS REVIEW CONTAINS SPOILERS, DOUBLE CLICK IF YOU CAN HANDLE THE TRUTH";
                Review review = new Review(reviewID, userReview, ratingVal, thoughts, actual_review, likesCount, dislikesCount);
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findUsername);
            DBUtils.closeStatement(findRating);
            DBUtils.closeResult(usernameSet);
            DBUtils.closeResult(ratingSet);
            DBUtils.closeConnection(connection);
        }

        return reviews;
    }
    public static ObservableList<Movie> SearchForMyMovies() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findDirector = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT m.*, r.rating FROM ratings r\n" +
                    "JOIN movie m ON r.movie_id = m.id\n" +
                    "WHERE r.user_id = ?\n");
            findDirector = connection.prepareStatement("SELECT first_name, last_name FROM director WHERE id = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No film with this title was found");
                alert.show();
            } else {
                try {
                    while (resultSet.next()) {
                        String title = resultSet.getString("Title");
                        Integer userID = resultSet.getInt("rating");
                        Integer year = resultSet.getInt("year");
                        Integer directorId = resultSet.getInt("Director");
                        String description = resultSet.getString("description");
                        String genre = resultSet.getString("genre");
                        Double avrRating = resultSet.getDouble("avr_rating");
                        Integer id = resultSet.getInt("id");
                        if (avrRating == null)
                            avrRating = 0.0;

                        findDirector.setInt(1, directorId);
                        directorSet = findDirector.executeQuery();

                        String director = null;
                        while (directorSet.next()) {
                            director = directorSet.getString("first_name") + " " + directorSet.getString("last_name");
                        }

                        Movie movie = new Movie(title, userID, year, director, description, genre, avrRating, id);
                        movies.add(movie);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findDirector);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
        return movies;
    }

    public static ObservableList<Movie> SearchForMyWatchlist() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findDirector = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT m.* FROM movie m\n" +
                    "JOIN watchlist w ON m.id = w.\"movieID\"\n" +
                    "WHERE w.\"userID\" = ?");
            findDirector = connection.prepareStatement("SELECT first_name, last_name FROM director WHERE id = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String title = resultSet.getString("Title");
                Integer year = resultSet.getInt("year");
                Integer directorId = resultSet.getInt("Director");
                String description = resultSet.getString("description");
                String genre = resultSet.getString("genre");
                Double avrRating = resultSet.getDouble("avr_rating");
                Integer id = resultSet.getInt("id");
                if (avrRating == null)
                    avrRating = 0.0;
                findDirector.setInt(1, directorId);
                directorSet = findDirector.executeQuery();
                String director = null;
                while (directorSet.next()) {
                    director = directorSet.getString("first_name") + " " + directorSet.getString("last_name");
                }
                Movie movie = new Movie(title, 0,year, director, description, genre, avrRating, id);
                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findDirector);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
        return movies;
    }
    public static ObservableList<Movie> SearchForTopMovies() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findDirector = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("""
                    SELECT m.*\s
                                                            FROM movie m
                                                            FULL JOIN ratings r ON m.id = r.movie_id
                                                            GROUP BY m.avr_rating, id,"Title", year,"Director", description, genre, movie_id
                                                            HAVING COUNT(r.*) >= 0
                                                            ORDER BY m.avr_rating DESC
                                                        LIMIT 50;""");
            findDirector = connection.prepareStatement("SELECT first_name, last_name FROM director WHERE id = ?");
            //preparedStatement.setInt(1, 0);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                Integer year = resultSet.getInt("year");
                Integer directorId = resultSet.getInt("Director");
                String description = resultSet.getString("description");
                String genre = resultSet.getString("genre");
                Double avrRating = resultSet.getDouble("avr_rating");
                Integer id = resultSet.getInt("id");
                if (avrRating == null)
                    avrRating = 0.0;
                findDirector.setInt(1, directorId);
                directorSet = findDirector.executeQuery();
                String director = null;
                while (directorSet.next()) {
                    director = directorSet.getString("first_name") + " " + directorSet.getString("last_name");
                }
                preparedStatement = connection.prepareStatement("SELECT * FROM ratings WHERE movie_id = ? and user_id = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, userID);
                directorSet = preparedStatement.executeQuery();
                Integer rt = null;
                while(directorSet.next())
                    rt = directorSet.getInt("rating");
                if (rt == null)
                    rt = 0;

                Movie movie = new Movie(title, rt, year, director, description, genre, avrRating, id);
                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findDirector);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
        return movies;
    }
    public static void deleteReview(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM liked_review\n" +
                    "WHERE \"reviewID\" = ?");
            preparedStatement.setInt(1, choosenReview.getReviewID());
            int rows =  preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM review\n" +
                    "WHERE review_id  = ?");
            preparedStatement.setInt(1, choosenReview.getReviewID());
            rows =  preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }
    public static void deleteList(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("DELETE FROM list_to_movie\n" +
                    "WHERE list_id = ?");
            preparedStatement.setInt(1, choosenList.getId());
            int rows =  preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM \"List\"\n" +
                    "WHERE id  = ?");
            preparedStatement.setInt(1, choosenList.getId());
            rows =  preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }
    public static ObservableList<Movie> SearchForMoviesInList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findDirector = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT m.* FROM movie m\n" +
                    "JOIN list_to_movie ON m.id = list_to_movie.movie_id\n" +
                    "WHERE list_id = ?");
            findDirector = connection.prepareStatement("SELECT first_name, last_name FROM director WHERE id = ?");
            preparedStatement.setInt(1, choosenList.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No film with this title was found");
                alert.show();
            } else {
                    while (resultSet.next()) {
                        String title = resultSet.getString("Title");
                        Integer year = resultSet.getInt("year");
                        Integer directorId = resultSet.getInt("Director");
                        String description = resultSet.getString("description");
                        String genre = resultSet.getString("genre");
                        Double avrRating = resultSet.getDouble("avr_rating");
                        Integer id = resultSet.getInt("id");
                        if (avrRating == null)
                            avrRating = 0.0;

                        findDirector.setInt(1, directorId);
                        directorSet = findDirector.executeQuery();

                        String director = null;
                        while (directorSet.next()) {
                            director = directorSet.getString("first_name") + " " + directorSet.getString("last_name");
                        }

                        preparedStatement = connection.prepareStatement("SELECT * FROM ratings WHERE movie_id = ? and user_id = ?");
                        preparedStatement.setInt(1, id);
                        preparedStatement.setInt(2, userID);
                        directorSet = preparedStatement.executeQuery();
                        Integer rt = null;
                        while(directorSet.next())
                            rt = directorSet.getInt("rating");
                        if (rt == null)
                            rt = 0;

                        Movie movie = new Movie(title, rt, year, director, description, genre, avrRating, id);
                        movies.add(movie);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findDirector);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
        return movies;
    }

    public static ObservableList<List> SearchForMyLists() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<List> lists = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM \"List\" WHERE \"userID\" = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer listID = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date_added");
                List list = new List(listID, name, description, date);
                lists.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
        return lists;
    }

    public static ObservableList<Movie> SearchForMovie(String Title) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement findDirector = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE \"Title\" LIKE ?");
            findDirector = connection.prepareStatement("SELECT first_name, last_name FROM director WHERE id = ?");
            String searchFor = Title + "%";
            preparedStatement.setString(1, searchFor);
            resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No film with this title was found");
                alert.show();
            } else {
                try {
                    while (resultSet.next()) {
                        String title = resultSet.getString("Title");
                        Integer year = resultSet.getInt("year");
                        Integer directorId = resultSet.getInt("Director");
                        String description = resultSet.getString("description");
                        String genre = resultSet.getString("genre");
                        Double avrRating = resultSet.getDouble("avr_rating");
                        Integer id = resultSet.getInt("id");
                        if (avrRating == null)
                            avrRating = 0.0;

                        findDirector.setInt(1, directorId);
                        directorSet = findDirector.executeQuery();

                        String director = null;
                        while (directorSet.next()) {
                            director = directorSet.getString("first_name") + " " + directorSet.getString("last_name");
                        }

                        Movie movie = new Movie(title, year, director, description, genre, avrRating, id);
                        movies.add(movie);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeStatement(findDirector);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
        return movies;
    }

    public static void insertMovie(ActionEvent event, String title, Integer year, String genre, String director, String description) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckMovieExists = null;
        PreparedStatement getDirectorID = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            String[] nameParts = director.split("\\s");
            if(nameParts.length == 2) {
                getDirectorID = connection.prepareStatement("SELECT * FROM director WHERE first_name = ? and last_name = ?");
                getDirectorID.setString(1, nameParts[0]);
                getDirectorID.setString(2, nameParts[1]);
                directorSet =  getDirectorID.executeQuery();
                if (!directorSet.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided director name is invalid");
                    alert.show();
                }
                else {
                    Integer directorID = null;
                    while(directorSet.next()){
                        directorID = directorSet.getInt("id");
                    }
                    psCheckMovieExists = connection.prepareStatement("SELECT * FROM movie WHERE \"Title\" = ? and \"Director\" = ?");
                    psCheckMovieExists.setString(1, title);
                    psCheckMovieExists.setInt(2, directorID);
                    resultSet = psCheckMovieExists.executeQuery();
                    if (resultSet.isBeforeFirst()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("This movie is ALREADY in the Cinequest DataBase!");
                        alert.show();
                    }
                    else {
                        psInsert = connection.prepareStatement("INSERT INTO movie (\"Title\", year, \"Director\", description, genre, avr_rating) values (?, ?, ?, ?, ?, ?)");
                        psInsert.setString(1, title);
                        psInsert.setInt(2, year);
                        psInsert.setInt(3, directorID);
                        psInsert.setString(4, description);
                        psInsert.setString(5, genre);
                        psInsert.setFloat(6, 0);
                        int rowsAffected = psInsert.executeUpdate();

                        changeScene(event, "mainpg.fxml", "You shall not pass");
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided director name is invalid");
                alert.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(psCheckMovieExists);
            DBUtils.closeStatement(psInsert);
            DBUtils.closeStatement(getDirectorID);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
    }

    public static void addToList(String movieTitle, String director){
        Connection connection = null;
        PreparedStatement psCheckMovieExists = null;
        PreparedStatement getDirectorID = null;
        ResultSet resultSet = null;
        ResultSet directorSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            String[] nameParts = director.split("\\s");
            if(nameParts.length == 2) {
                getDirectorID = connection.prepareStatement("SELECT * FROM director WHERE first_name = ? and last_name = ?");
                getDirectorID.setString(1, nameParts[0]);
                getDirectorID.setString(2, nameParts[1]);
                directorSet =  getDirectorID.executeQuery();
                if (!directorSet.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided director name is invalid");
                    alert.show();
                }
                else {
                    Integer directorID = null;
                    while(directorSet.next()){
                        directorID = directorSet.getInt("id");
                    }
                    psCheckMovieExists = connection.prepareStatement("SELECT id FROM movie WHERE \"Title\" = ? and \"Director\" = ?");
                    psCheckMovieExists.setString(1, movieTitle);
                    psCheckMovieExists.setInt(2, directorID);
                    resultSet = psCheckMovieExists.executeQuery();
                    if (!resultSet.isBeforeFirst()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("This movie does not exist");
                        alert.show();
                    }
                    else {
                        Integer movieID = null;
                        while (resultSet.next()) {
                            movieID = resultSet.getInt("id");
                        }
                        psCheckMovieExists = connection.prepareStatement("SELECT * FROM list_to_movie WHERE  list_id = ? AND movie_id = ?");
                        psCheckMovieExists.setInt(1, choosenList.getId());
                        psCheckMovieExists.setInt(2, movieID);
                        resultSet = psCheckMovieExists.executeQuery();
                        if (resultSet.isBeforeFirst()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This movie is already in your list");
                            alert.show();
                        } else {
                            psCheckMovieExists = connection.prepareStatement("INSERT INTO list_to_movie (list_id, movie_id) VALUES (?, ?)");
                            psCheckMovieExists.setInt(1, choosenList.getId());
                            psCheckMovieExists.setInt(2, movieID);
                            int rows = psCheckMovieExists.executeUpdate();
                        }
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided director name is invalid");
                alert.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(psCheckMovieExists);
            DBUtils.closeStatement(getDirectorID);
            DBUtils.closeResult(directorSet);
            DBUtils.closeConnection(connection);
        }
    }

    public static void insertList(ActionEvent actionEvent, String name, String description) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckListExists = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            psCheckListExists = connection.prepareStatement("SELECT name FROM \"List\" WHERE name = ? and \"userID\" = ?");
            psCheckListExists.setString(1, name);
            psCheckListExists.setInt(2, DBUtils.getUserID());
            resultSet = psCheckListExists.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                psInsert = connection.prepareStatement("INSERT INTO \"List\" (\"userID\", name, description, date_added) values (?, ?, ?, ?)");
                psInsert.setDate(4, Date.valueOf(LocalDate.now()));
                psInsert.setInt(1, DBUtils.getUserID());
                psInsert.setString(2, name);
                psInsert.setString(3, description);
                int rowsAffected = psInsert.executeUpdate();
                psInsert = connection.prepareStatement("SELECT * FROM \"List\" WHERE name = ?");
                psInsert.setString(1, name);
                resultSet = psInsert.executeQuery();
                while (resultSet.next()){
                    choosenList = new List(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getDate("date_added"));
                }
                DBUtils.changeScene(actionEvent, "seeList.fxml", ".");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You already have a list with this name");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(psCheckListExists);
            DBUtils.closeStatement(psInsert);
            DBUtils.closeConnection(connection);
        }
    }
    public static void insertDirector(ActionEvent actionEvent, String first, String last) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckListExists = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            psCheckListExists = connection.prepareStatement("SELECT * FROM director WHERE first_name = ? AND last_name = ?");
            psCheckListExists.setString(1, first);
            psCheckListExists.setString(2, last);
            resultSet = psCheckListExists.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                psInsert = connection.prepareStatement("INSERT INTO director (first_name, last_name) VALUES (?, ?)");
                psInsert.setString(1, first);
                psInsert.setString(2, last);
                int rowsAffected = psInsert.executeUpdate();
                DBUtils.changeScene(actionEvent, "mainpg.fxml", ".");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This director in already in cinequest database");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(psCheckListExists);
            DBUtils.closeStatement(psInsert);
            DBUtils.closeConnection(connection);
        }
    }

    public static void insertReview(ActionEvent actionEvent, String text, Boolean spoilers){
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            psInsert = connection.prepareStatement("INSERT INTO \"review\" (review_text, spoiler, user_id, movie_id) VALUES (?, ?, ?, ?)");
            psInsert.setString(1, text);
            psInsert.setBoolean(2, spoilers);
            psInsert.setInt(3, DBUtils.getUserID());
            psInsert.setInt(4, DBUtils.getMovie());
            int rowsAffected = psInsert.executeUpdate();
            DBUtils.changeScene(actionEvent, "Film.fxml", "May the force be with you");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStatement(psInsert);
            closeConnection(connection);
        }
    }

    public static void closeConnection(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeStatement(PreparedStatement preparedStatement){
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResult(ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setMovieDetails(Label title, Label director, Label year, Text description, Label watchedBy, Label avr, Label rank, Button watchlist, Button[] array){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
            preparedStatement.setInt(1, movie);
            resultSet = preparedStatement.executeQuery();
            String titleOfMovie;
            Integer directorID = null;
            Integer yearInt;
            while (resultSet.next()){
                titleOfMovie = resultSet.getString("Title");
                title.setText(titleOfMovie);
                DBUtils.setMovieTitle(titleOfMovie);
                yearInt = resultSet.getInt("year");
                year.setText(yearInt.toString());
                directorID = resultSet.getInt("Director");
                description.setText(resultSet.getString("description"));
                Float avr_rate;
                avr_rate = resultSet.getFloat("avr_rating");
                DBUtils.setRatingOfTheMovie(avr_rate);
                avr.setText("AVERAGE RATING:" + avr_rate.toString());
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM director WHERE id = ?");
            preparedStatement.setInt(1, directorID);
            resultSet = preparedStatement.executeQuery();
            String firstName = null, lastName = null;
            while (resultSet.next()){
                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
            }
            director.setText(firstName + " " + lastName);

            preparedStatement = connection.prepareStatement("SELECT count(*) FROM ratings WHERE movie_id = ?");
            preparedStatement.setInt(1, movie);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer numberOfWatches = resultSet.getInt("count");
                String numberString = numberOfWatches.toString();
                DBUtils.setWatchedByNumber(numberOfWatches);
                watchedBy.setText("WATCHED BY: " + numberString);
            }

            preparedStatement = connection.prepareStatement("SELECT row_number() over (ORDER BY id) AS position FROM movie WHERE id = ?");
            preparedStatement.setInt(1, movie);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer rankInt = resultSet.getInt("position");
                String rankString = rankInt.toString();
                rank.setText("RANK: " + rankString);
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM watchlist WHERE \"movieID\" = ? and \"userID\" = ?");
            preparedStatement.setInt(1, movie);
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                watchlist.setStyle("-fx-background-color:  #2f5830");
            } else watchlist.setStyle("-fx-background-color:  #686e6e");

            preparedStatement = connection.prepareStatement("SELECT rating FROM ratings WHERE movie_id = ? AND user_id = ?");
            preparedStatement.setInt(1, movie);
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer rating = resultSet.getInt("rating");
                for (int i = 0; i < rating; i++){
                    array[i].setStyle("-fx-background-color:  #2f5830");
                }
                for (int i = rating; i < 10; i++){
                    array[i].setStyle("-fx-background-color:  #686e6e");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }
    public static void addToWatchlist(Button add){
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            if(add.getStyle().equals("-fx-background-color:  #2f5830")){
                psInsert = connection.prepareStatement("DELETE FROM watchlist\n" +
                        "WHERE \"userID\" = ? AND \"movieID\" = ?;");
                add.setStyle("-fx-background-color:  #686e6e");
            } else {

                psInsert = connection.prepareStatement("INSERT INTO watchlist (\"userID\", \"movieID\") VALUES (?, ?)");
                add.setStyle("-fx-background-color:  #2f5830");
            }
            psInsert.setInt(1, getUserID());
            psInsert.setInt(2, getMovie());
            int rowsAffected = psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStatement(psInsert);
            closeConnection(connection);
        }
    }

    public static void addRating(Integer rating, Button[] array){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM ratings WHERE movie_id = ? AND user_id = ?");
            preparedStatement.setInt(1, movie);
            preparedStatement.setInt(2, userID);

            resultSet = preparedStatement.executeQuery();
            int affected_rows;
            int updateRating = 0;
            Float newRating;
            if(resultSet.isBeforeFirst()){
                while(resultSet.next()){
                    updateRating = resultSet.getInt("rating");
                }
                preparedStatement = connection.prepareStatement("UPDATE ratings SET rating = ? WHERE movie_id = ? AND user_id = ?");
                preparedStatement.setFloat(1, rating);
                preparedStatement.setInt(2, movie);
                preparedStatement.setInt(3, userID);
                affected_rows =  preparedStatement.executeUpdate();

                newRating = (watchedByNumber * ratingOfTheMovie - updateRating + rating) / watchedByNumber;
                System.out.println(watchedByNumber + " " + ratingOfTheMovie + " " + updateRating + " " + rating + " " + newRating);
            }
            else{
                preparedStatement = connection.prepareStatement("INSERT INTO ratings (user_id, movie_id, rating) VALUES (?, ?, ?)");
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, movie);
                preparedStatement.setFloat(3, rating);
                affected_rows =  preparedStatement.executeUpdate();
                newRating = (watchedByNumber * ratingOfTheMovie + rating) / (watchedByNumber + 1);
                DBUtils.setWatchedByNumber(watchedByNumber + 1);
            }

            DBUtils.setRatingOfTheMovie(newRating);

            preparedStatement = connection.prepareStatement("UPDATE movie SET avr_rating = ? WHERE id = ?");
            preparedStatement.setFloat(1, newRating);
            preparedStatement.setInt(2, movie);
            affected_rows = preparedStatement.executeUpdate();

            for (int i = 0; i < rating; i++){
                array[i].setStyle("-fx-background-color:  #2f5830");
            }
            for (int i = rating; i < 10; i++){
                array[i].setStyle("-fx-background-color:  #686e6e");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public static void setLikedButton(Button buttonYes, Button buttonNo){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM liked_review WHERE \"reviewID\" = ? and \"userID\" = ?");
            preparedStatement.setInt(1, choosenReview.getReviewID());
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()){
                while (resultSet.next()){
                    if(resultSet.getBoolean("liked")){
                        buttonYes.setStyle("-fx-background-color:  #591b12");
                        buttonNo.setStyle("-fx-background-color: #808080");
                    }
                    else {
                        buttonYes.setStyle("-fx-background-color: #808080");
                        buttonNo.setStyle("-fx-background-color:  #35638C");
                    }
                }
            }
            else {
                buttonYes.setStyle("-fx-background-color: #808080");
                buttonNo.setStyle("-fx-background-color: #808080");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public static void likeReview(Integer reviewID, Boolean liked, Button like, Button dislike){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinequest", "postgres", "password");
            preparedStatement = connection.prepareStatement("SELECT * FROM liked_review WHERE \"reviewID\" = ? and \"userID\" = ?");
            preparedStatement.setInt(1, reviewID);
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()){
                preparedStatement = connection.prepareStatement("UPDATE liked_review SET liked = ? WHERE \"reviewID\" = ? and \"userID\" = ?");
                preparedStatement.setBoolean(1, liked);
                preparedStatement.setInt(2, reviewID);
                preparedStatement.setInt(3, userID);
                int rows = preparedStatement.executeUpdate();
            }
            else {
                    preparedStatement = connection.prepareStatement("INSERT INTO liked_review (\"reviewID\", \"userID\", liked) VALUES (?, ?, ?)");
                    preparedStatement.setInt(1, reviewID);
                    preparedStatement.setInt(2, DBUtils.getUserID());
                    preparedStatement.setBoolean(3, liked);
                    int rows = preparedStatement.executeUpdate();
            }
            if (liked == Boolean.TRUE){
                like.setStyle("-fx-background-color:  #591b12");
                dislike.setStyle("-fx-background-color: #808080");
            }
            else{
                like.setStyle("-fx-background-color: #808080");
                dislike.setStyle("-fx-background-color:  #35638C");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResult(resultSet);
            DBUtils.closeStatement(preparedStatement);
            DBUtils.closeConnection(connection);
        }
    }

    public static Boolean getGoBackFromReview() {
        return goBackFromReview;
    }

    public static void setGoBackFromReview(Boolean goBackFromReview) {
        DBUtils.goBackFromReview = goBackFromReview;
    }
}