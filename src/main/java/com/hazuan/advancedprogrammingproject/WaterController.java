package com.hazuan.advancedprogrammingproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class WaterController implements Initializable {

    String username;
    Parent root;
    private static Statement stmt;
    @FXML
    Label lblWaterIntake;
    @FXML
    Label lblWaterLevel;

    public void dashboardBtnEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard-view.fxml"));
        root = loader.load();

        //sending username to DashboardController
        DashboardController dashboardController = loader.getController();
        dashboardController.setUsername(username);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    public void setUsername(String username){
        this.username = username;
        String query = "SELECT water_intake, water_level FROM Userdata WHERE username = ?";
        try (PreparedStatement pstmt = stmt.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username.trim());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                lblWaterIntake.setText(resultSet.getString(1));
                lblWaterLevel.setText(resultSet.getString(2));
            }
        }catch (Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    private static void initializeDB() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection connection = DriverManager.getConnection("jdbc:derby:javabook;create=true;user=scott;password=tiger");
            stmt = connection.createStatement();
        } catch (Exception ex) {
            System.out.println("Error during DB initialization: " + ex.getMessage());
            System.out.println("Error: " + ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDB();
    }
}
