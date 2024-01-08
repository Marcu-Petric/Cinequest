package com.example.proiect;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUp_Controler implements Initializable {

    @FXML
    private Button button_createacc;

    @FXML
    private CheckBox cb_agree;

    @FXML
    private PasswordField pf_repeatedpassword;

    @FXML
    private PasswordField pf_password;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tf_email.setStyle("-fx-text-fill: white;");

        button_createacc.setOnAction(actionEvent -> {
            if (!tf_username.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty() &&
                    !tf_email.getText().trim().isEmpty() && !pf_repeatedpassword.getText().trim().isEmpty()){
                String password = pf_password.getText();
                String passwordRepeated = pf_repeatedpassword.getText();
                if(password.equals(passwordRepeated)) {
                    if(cb_agree.isSelected()){
                        DBUtils.signUpUser(actionEvent, tf_username.getText(),password, tf_email.getText());
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You need to agree to our condition to move forward");
                        alert.show();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Passwords do not match");
                    alert.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information");
                alert.show();
            }
        });
    }
}
