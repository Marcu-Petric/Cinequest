package com.example.proiect;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewFilm implements Initializable {

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
    private Label label_title;

    @FXML
    private Label label_director;

    @FXML
    private Label label_year;

    @FXML
    private Text text_description;

    @FXML
    private Label label_watchedBy;

    @FXML
    private Label label_avr;

    @FXML
    private Label label_rank;

    @FXML
    private Button button_add;

    @FXML
    private Button button_review;

    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10;

    @FXML
    private Button button_seeReviews;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button[] arrayButtons = new Button[10];
        arrayButtons[0] = button1;
        arrayButtons[1] = button2;
        arrayButtons[2] = button3;
        arrayButtons[3] = button4;
        arrayButtons[4] = button5;
        arrayButtons[5] = button6;
        arrayButtons[6] = button7;
        arrayButtons[7] = button8;
        arrayButtons[8] = button9;
        arrayButtons[9] = button10;
        DBUtils.setMovieDetails(label_title, label_director, label_year, text_description, label_watchedBy, label_avr, label_rank, button_add, arrayButtons);
        button_home.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "Welcome!"));
        button_films.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "filmsPage.fxml", "Your movies"));
        button_lists.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "lists.fxml", "Your Lists"));
        button_reviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "reviewPage.fxml", "Your Reviews"));
        button_search.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "search.fxml", "Search your movies"));
        button_watchlist.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "watchlist.fxml", "Your Reviews"));
        button_signout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "hello-view.fxml", "Your Reviews"));
        button_add.setOnAction(actionEvent -> DBUtils.addToWatchlist(button_add));
        button_review.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "review.fxml", "Share your thoughts"));
        button1.setOnAction(actionEvent -> DBUtils.addRating(1, arrayButtons));
        button2.setOnAction(actionEvent -> DBUtils.addRating(2, arrayButtons));
        button3.setOnAction(actionEvent -> DBUtils.addRating(3, arrayButtons));
        button4.setOnAction(actionEvent -> DBUtils.addRating(4, arrayButtons));
        button5.setOnAction(actionEvent -> DBUtils.addRating(5, arrayButtons));
        button6.setOnAction(actionEvent -> DBUtils.addRating(6, arrayButtons));
        button7.setOnAction(actionEvent -> DBUtils.addRating(7, arrayButtons));
        button8.setOnAction(actionEvent -> DBUtils.addRating(8, arrayButtons));
        button9.setOnAction(actionEvent -> DBUtils.addRating(9, arrayButtons));
        button10.setOnAction(actionEvent -> DBUtils.addRating(10, arrayButtons));
        button_seeReviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "seeReviews.fxml", "."));;
    }

}
