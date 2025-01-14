package com.hazuan.advancedprogrammingproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DashboardController {
    String username;

    @FXML
    Circle connectIndicator;
    @FXML
    Button btnConnect;
    @FXML
    Label lblHi;
    public void connButtonEvent(){
        if (connectIndicator.getFill() == Color.RED){
            connectIndicator.setFill(Color.GREEN);
            btnConnect.setText("Connected");
        } else {
            connectIndicator.setFill(Color.RED);
            btnConnect.setText("Connect");
        }
    }

    public void setUsername(String username){
        this.username = username;
        lblHi.setText("Hi, " + this.username);
    }
}
