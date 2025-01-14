package com.hazuan.advancedprogrammingproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class LoginController {
    private static Statement stmt;
    Parent root;

    @FXML
    Button btnLogin;
    @FXML
    Button btnCancel;
    @FXML
    TextField tfUsername;
    @FXML
    TextField tfPassword;
    @FXML
    Label lblInvalid;


    public void loginEvent(ActionEvent event)  {
        initializeDB();
        String query = "SELECT count(1) FROM Scores WHERE username = ? AND password = ?";
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        if (!tfUsername.getText().isBlank() && !tfPassword.getText().isBlank()){
            try (PreparedStatement pstmt = stmt.getConnection().prepareStatement(query)) {
                pstmt.setString(1, username.trim());
                pstmt.setString(2, password.trim());
                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()){
                    if (resultSet.getInt(1) == 1){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard-view.fxml"));
                        root = loader.load();

                        DashboardController dashboardController = loader.getController();
                        dashboardController.setUsername(username);
                        //Parent root = FXMLLoader.load(getClass().getResource("Dashboard-view.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }else {
                        lblInvalid.setText("Username or Password is invalid");
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void closeEvent(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
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