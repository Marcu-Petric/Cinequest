package com.example.proiect;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeControler implements Initializable {

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
    private Button button_addMovie;

    @FXML
    private Button button_addDirector;

    @FXML
    private Button button_addList;

    @FXML
    private Button button_50;

    @FXML
    private Button button_ff;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_home.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "mainpg.fxml", "Welcome!"));
        button_films.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "filmsPage.fxml", "Your movies"));
        button_lists.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "lists.fxml", "Your Lists"));
        button_reviews.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "reviewPage.fxml", "Your Reviews"));
        button_search.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "search.fxml", "Search your movies"));
        button_watchlist.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "watchlist.fxml", "Your Reviews"));
        button_signout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "hello-view.fxml", "Your Reviews"));
        button_addMovie.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "AddMovie.fxml", "Your Reviews"));
        button_addDirector.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "addDirector.fxml", "Your Reviews"));
        button_addList.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "AddList.fxml", "Your Reviews"));
        button_ff.setOnAction(actionEvent -> {
            Date date = new Date(2024-1-7);
            DBUtils.setChoosenList(new List(5, "Founder's favourites", "Premade List for the main page", date));
            DBUtils.changeScene(actionEvent, "seeList.fxml", "The crème de la crème");
        });
        button_50.setOnAction(actionEvent -> {
            DBUtils.changeScene(actionEvent, "top50.fxml", ".");
        });
    }

}
