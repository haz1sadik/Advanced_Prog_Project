package com.hazuan.advancedprogrammingproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HealthController {
    @FXML
    Label lblTestLabel;
    String username;
    public void setUsername(String username){
        this.username = username;
        lblTestLabel.setText("Scene Health Info, " + this.username);
    }
}
