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
import java.util.Date;
import java.util.ResourceBundle;

public class ListsController implements Initializable {
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
    private TableView<List> tableView;

    @FXML
    private TableColumn<List, String> tc_name;

    @FXML
    private TableColumn<List, String >tc_description;

    @FXML
    private TableColumn<List, Date> tc_date;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.tc_date.setCellValueFactory(new PropertyValueFactory<>("date"));

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
                List selected = tableView.getSelectionModel().getSelectedItem();
                DBUtils.setChoosenList(selected);
                ActionEvent actionEvent = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
                DBUtils.changeScene(actionEvent, "seeList.fxml", ".");
            }
        });
    }

    public void setResults(){
        ObservableList<List> lists = DBUtils.SearchForMyLists();
        this.tableView.getColumns().clear();
        this.tableView.setItems(lists);
        this.tableView.getColumns().addAll(tc_name, tc_description, tc_date);
    }
}
