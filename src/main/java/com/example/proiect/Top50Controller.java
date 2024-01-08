package com.example.proiect;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Top50Controller implements Initializable {
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
    private Label label_name;

    @FXML
    private Text text;

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

    @FXML
    private TableColumn<Movie, Integer> tc_userRating;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List chosen = DBUtils.getChoosenList();
        label_name.setText("Cinequest top 50");
        text.setText("These are our community favourite movies, only the ones with number of rating over 100 are taken into consideration");

        this.tc_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.tc_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        this.tc_director.setCellValueFactory(new PropertyValueFactory<>("director"));
        this.tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.tc_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        this.tc_avrRating.setCellValueFactory(new PropertyValueFactory<>("avrRating"));
        this.tc_userRating.setCellValueFactory(new PropertyValueFactory<>("userRating"));
        this.setResults();

        button_home.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "Welcome!"));
        button_films.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "filmsPage.fxml", "Your movies"));
        button_lists.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "lists.fxml", "Your Lists"));
        button_reviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "reviewPage.fxml", "Your Reviews"));
        button_search.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "search.fxml", "Your Reviews"));
        button_watchlist.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "watchlist.fxml", "Your Reviews"));
        button_signout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "hello-view.fxml", "Your Reviews"));

        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Movie selected = tableView.getSelectionModel().getSelectedItem();
                DBUtils.setMovie(selected.getId());
                ActionEvent actionEvent = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
                DBUtils.changeScene(actionEvent, "Film.fxml", "the movie you chose");
            }
        });
    }

    public void setResults(){
        ObservableList<Movie> movies = DBUtils.SearchForTopMovies();
        this.tableView.getColumns().clear();
        this.tableView.setItems(movies);
        this.tableView.getColumns().addAll(tc_title, tc_year, tc_director, tc_genre, tc_description, tc_avrRating, tc_userRating);
    }
}
