package com.hazuan.advancedprogrammingproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DashboardController {
    String username;
    Parent root;
    private static Statement  stmt;

    @FXML
    Circle connectIndicator;
    @FXML
    Button btnConnect;
    @FXML
    Label lblHi, lblDistanceKM, lblSteps, lblElevationM, lblPace, lblCalories;
    @FXML
    Button btnUserInfo, btnHealth, btnWater;

    public void connBtnEvent(){
        initializeDB();
        String query = "SELECT name, steps, distance_km, elevation_m, pace, calories_burnt FROM Userdata WHERE username = ?";
        try (PreparedStatement pstmt = stmt.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username.trim());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                lblSteps.setText(resultSet.getString(2) + " steps");
                lblDistanceKM.setText(resultSet.getString(3) + " KM");
                lblElevationM.setText(resultSet.getString(4) + "M");
                lblPace.setText(resultSet.getString(5));
                lblCalories.setText(resultSet.getString(6));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if (connectIndicator.getFill() == Color.RED){
            connectIndicator.setFill(Color.GREEN);
            btnConnect.setText("Connected");
        } else {
            connectIndicator.setFill(Color.RED);
            btnConnect.setText("Connect");
        }
    }

    public void userInfoBtnEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInfo-view.fxml"));
        root = loader.load();

        //sending username to DashboardController
        UserInfoController userInfoController = loader.getController();
        userInfoController.setUsername(username);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void healthBtnEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Health-view.fxml"));
        root = loader.load();

        //sending username to DashboardController
        HealthController healthController = loader.getController();
        healthController.setUsername(username);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void waterBtnEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Water-view.fxml"));
        root = loader.load();

        //sending username to DashboardController
        WaterController waterController = loader.getController();
        waterController.setUsername(username);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setUsername(String username, Statement stmt){
        this.username = username;
        this.stmt = stmt;
        lblHi.setText("Hi, " + this.username);
    }

    private static void initializeDB() {
        try {
            // Load the JDBC driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            // Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:derby:javabook;create=true;user=scott;password=tiger");
            // Initialize the statement object
            stmt = connection.createStatement();
        } catch (Exception ex) {
            System.out.println("Error during DB initialization: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
