package com.example.proiect;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SeeReviewsController implements Initializable {
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
    private TableColumn<Review, String> tc_username;

    @FXML
    private TableColumn<Review, Integer> tc_rating;

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
        this.tc_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.tc_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        this.tc_thoughts.setCellValueFactory(new PropertyValueFactory<>("thoughts"));
        this.tc_likes.setCellValueFactory(new PropertyValueFactory<>("likesCount"));
        this.tc_dislikes.setCellValueFactory(new PropertyValueFactory<>("dislikeCount"));
        this.setResults(0);

        button_home.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "Welcome!"));
        button_films.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "filmsPage.fxml", "Your movies"));
        button_lists.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "lists.fxml", "Your Lists"));
        button_reviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "reviewPage.fxml", "Your Reviews"));
        button_search.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "search.fxml", "Your Reviews"));
        button_watchlist.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "watchlist.fxml", "Your Reviews"));
        button_signout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "hello-view.fxml", "Your Reviews"));
        button_back.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "Film.fxml", "."));

        button_most.setOnAction(actionEvent -> setResults(1));
        button_least.setOnAction(actionEvent -> setResults(2));

        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                Review selected = tableView.getSelectionModel().getSelectedItem();
                DBUtils.setChoosenReview(selected);
                DBUtils.setGoBackFromReview(Boolean.TRUE);
                ActionEvent actionEvent = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
                DBUtils.changeScene(actionEvent, "reviewSee.fxml", "the movie you chose");
            }
        });
    }

    public void setResults(Integer x){
        ObservableList<Review> reviews = DBUtils.searchForReviews(x);
        this.tableView.getColumns().clear();
        this.tableView.setItems(reviews);
        this.tableView.getColumns().addAll(tc_username, tc_rating, tc_likes, tc_dislikes, tc_thoughts);
    }
}
