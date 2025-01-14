package com.hazuan.advancedprogrammingproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserInfoController {
    String username;
     @FXML
     Label lblTestLabel;
    public void setUsername(String username){
        this.username = username;
        lblTestLabel.setText("Scene User Info, " + this.username);
    }
}
