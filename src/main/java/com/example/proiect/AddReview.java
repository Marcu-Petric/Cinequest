package com.example.proiect;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class AddReview implements Initializable {

    @FXML
    private Label label_title;

    @FXML
    private CheckBox cb_spoilers;

    @FXML
    private TextArea textArea;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label_title.setText(DBUtils.getMovieTitle());
        button_cancel.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent, "Film.fxml", "Share your thoughts"));
        button_save.setOnAction(actionEvent -> DBUtils.insertReview(actionEvent, textArea.getText(), cb_spoilers.isSelected()));
    }
}
