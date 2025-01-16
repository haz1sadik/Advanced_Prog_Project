package com.hazuan.advancedprogrammingproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    private static Statement stmt;
    @FXML
    public TextField tfUsername;
    @FXML
    public TextField tfPassword;
    @FXML
    public TextField tfFullName;
    @FXML
    public TextField tfAge;
    @FXML
    public TextField tfEmail;
    @FXML
    public TextField tfPhoneNumber;
    @FXML
    public TextField tfHeight;
    @FXML
    public TextField tfWeight;
    @FXML
    public Label lblInvalid;


    public void cancelEvent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void registerEvent(ActionEvent event) throws IOException {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String fullName = tfFullName.getText();
        int age = Integer.parseInt(tfAge.getText());
        String email = tfEmail.getText();
        String phoneNum = tfPhoneNumber.getText();
        double height = Double.parseDouble(tfHeight.getText());
        double weight = Double.parseDouble(tfWeight.getText());
        Random random = new Random();

        String query = "INSERT INTO userdata (USERNAME, PASSWORD, NAME, AGE, EMAIL, PHONE_NUMBER, HEIGHT_CM, WEIGHT_KG, STEPS, DISTANCE_KM, ELEVATION_M, PACE, CALORIES_BURNT, WORKOUT_TIME, RUNNING_TIME, CYCLING_TIME, WATER_INTAKE, WATER_LEVEL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, 0.0, 0, '0''0\"', 0, 0, 0, 0, 0, ?)";
        try (PreparedStatement pstmt = stmt.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());
            pstmt.setString(3, fullName.trim());
            pstmt.setInt(4, age);
            pstmt.setString(5, email.trim());
            pstmt.setString(6, phoneNum.trim());
            pstmt.setDouble(7, height);
            pstmt.setDouble(8, weight);
            pstmt.setInt(9, (random.nextInt(200 - 40 + 1) + 40));

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new employee was inserted successfully!");
                Parent root = FXMLLoader.load(getClass().getResource("Login-view.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (Exception ex){
            lblInvalid.setText("Invalid information entered");
        }
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
            System.out.println("Error: " + ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDB();
    }
}
