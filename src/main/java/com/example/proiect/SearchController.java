package com.example.proiect;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private Button button_home;

    @FXML
    private Button button_films;

    @FXML
    private Button button_lists;

    @FXML
    private Button button_reviews;

    @FXML
    private Button button_search;

    @FXML
    private Button button_watchlist;

    @FXML
    private Button button_signout;

    @FXML
    private Button button_start_search;

    @FXML
    private TextField tf_title;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> tc_title;

    @FXML
    private TableColumn<Movie, Integer> tc_year;

    @FXML
    private TableColumn<Movie, String> tc_director;

    @FXML
    private TableColumn<Movie, String> tc_description;

    @FXML
    private TableColumn<Movie, String> tc_genre;

    @FXML
    private TableColumn<Movie, Double> tc_avrRating;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tc_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.tc_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        this.tc_director.setCellValueFactory(new PropertyValueFactory<>("director"));
        this.tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.tc_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.tc_avrRating.setCellValueFactory(new PropertyValueFactory<>("avrRating"));

        button_home.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "Welcome!"));
        button_films.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "filmsPage.fxml", "Your movies"));
        button_lists.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "lists.fxml", "Your Lists"));
        button_reviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "reviewPage.fxml", "Your Reviews"));
        button_search.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "search.fxml", "Your Reviews"));
        button_watchlist.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "watchlist.fxml", "Your Reviews"));
        button_signout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "hello-view.fxml", "Your Reviews"));
        button_start_search.setOnAction(actionEvent -> SearchController.this.setResults(SearchController.this.tf_title.getText()));

        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Movie selected = tableView.getSelectionModel().getSelectedItem();
                DBUtils.setMovie(selected.getId());
                ActionEvent actionEvent = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
                DBUtils.changeScene(actionEvent, "Film.fxml", "the movie you chose");
            }
        });
    }

    public void setResults(String movieTitle){
        ObservableList<Movie> movies = DBUtils.SearchForMovie(movieTitle);
        this.tableView.getColumns().clear();
        this.tableView.setItems(movies);
        this.tableView.getColumns().addAll(tc_title, tc_year, tc_director, tc_genre, tc_description, tc_avrRating);
    }
}
