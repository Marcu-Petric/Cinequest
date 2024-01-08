package com.example.proiect;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewPageController implements Initializable {
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
    private Button button_most;

    @FXML
    private Button button_least;

    @FXML
    private TableView<Review> tableView;

    @FXML
    private TableColumn<Review, String> tc_movie;

    @FXML
    private TableColumn<Review, Integer> tc_likes;

    @FXML
    private TableColumn<Review, Integer> tc_dislikes;

    @FXML
    private TableColumn<Review, String> tc_thoughts;

    @FXML
    private Button button_back;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tc_movie.setCellValueFactory(new PropertyValueFactory<>("movie"));
        this.tc_thoughts.setCellValueFactory(new PropertyValueFactory<>("actual_review"));
        this.tc_likes.setCellValueFactory(new PropertyValueFactory<>("likesCount"));
        this.tc_dislikes.setCellValueFactory(new PropertyValueFactory<>("dislikeCount"));
        this.setResults(0);

        button_home.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "Welcome!"));
        button_films.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "filmsPage.fxml", "Your movies"));
        button_lists.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "lists.fxml", "Your Lists"));
        button_reviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "reviewPage.fxml", "Your Reviews"));
        button_search.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "search.fxml", "Search here!"));
        button_watchlist.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "watchlist.fxml", "Your Watchlist"));
        button_signout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "hello-view.fxml", "Welcome"));
        button_back.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "."));

        button_most.setOnAction(actionEvent -> setResults(1));
        button_least.setOnAction(actionEvent -> setResults(2));

        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Review selected = tableView.getSelectionModel().getSelectedItem();
                DBUtils.setMovieTitle(selected.getMovie());
                selected.setUsername(DBUtils.getUsername());
                DBUtils.setChoosenReview(selected);
                DBUtils.setGoBackFromReview(Boolean.FALSE);
                ActionEvent actionEvent = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
                DBUtils.changeScene(actionEvent, "reviewSee.fxml", ".");
            }
        });
    }

    public void setResults(Integer x){
        ObservableList<Review> reviews = DBUtils.searchForMyReviews(x);
        this.tableView.getColumns().clear();
        this.tableView.setItems(reviews);
        this.tableView.getColumns().addAll(tc_movie,tc_thoughts, tc_likes, tc_dislikes);
    }
}
