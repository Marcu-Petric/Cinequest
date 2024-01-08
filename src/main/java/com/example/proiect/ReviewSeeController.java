package com.example.proiect;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ReviewSeeController implements Initializable {

    @FXML
    private Label label_title;

    @FXML
    private Label label_username;

    @FXML
    private Text text;

    @FXML
    private Label label_rating;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_like;

    @FXML
    private Button button_dislike;

    @FXML
    private Button button_delete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Review selected = DBUtils.getChoosenReview();
        label_title.setText(DBUtils.getMovieTitle());
        label_username.setText(selected.getUsername());
        if(selected.getRating() != null)
            label_rating.setText(selected.getRating().toString());
        else
            label_rating.setText("NOT RATED");
        text.setText(selected.getActual_review());
        button_cancel.setOnAction(actionEvent -> {
            if (DBUtils.getGoBackFromReview())
                DBUtils.changeScene(actionEvent, "SeeReviews.fxml", ".");
            else
                DBUtils.changeScene(actionEvent, "reviewPage.fxml", ".");
        });
        if(DBUtils.getChoosenReview().getUsername().equals(DBUtils.getUsername())) {
            button_delete.setDisable(false);
            button_delete.setVisible(true);
        }  else {
            button_delete.setDisable(true);
            button_delete.setVisible(false);
        }
        button_delete.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this list?");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    DBUtils.deleteReview();
                    if (DBUtils.getGoBackFromReview())
                        DBUtils.changeScene(actionEvent, "SeeReviews.fxml", ".");
                    else
                        DBUtils.changeScene(actionEvent, "reviewPage.fxml", ".");
                }
            });
        });
        DBUtils.setLikedButton(button_like, button_dislike);
        button_like.setOnAction(actionEvent -> DBUtils.likeReview(selected.getReviewID(), Boolean.TRUE, button_like, button_dislike));
        button_dislike.setOnAction(actionEvent -> DBUtils.likeReview(selected.getReviewID(), Boolean.FALSE, button_like, button_dislike));
    }
}
