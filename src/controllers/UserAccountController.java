package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserAccountController {

    // ==================== Signin FXML Elements ====================
    @FXML
    private TextField EmailBox;

    @FXML
    private PasswordField PasswordBox;

    // ==================== Signup FXML Elements ====================
    @FXML
    private TextField signupEmail;

    @FXML
    private PasswordField signupPassword;

    @FXML
    private PasswordField confirmPassword;

 // ==================== Login Method ====================
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = EmailBox.getText();
        String password = PasswordBox.getText();

        if (email.isEmpty() || password.isEmpty()) return;

        // ⭐ Admin စကားလုံးပါဝင်မှုကို စစ်ဆေးခြင်း
        if (email.toLowerCase().contains("admin")) {
            System.out.println("❌ Access Denied: User account side cannot use admin keywords.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            // ... ကျန်တဲ့ login logic များ (Select Query) ...
            String sql = "SELECT * FROM logtable WHERE email=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // ... Success logic များ ...
                models.UserSession.setSession(email);
                
                if (Main_Controllers.instance != null) {
                    Main_Controllers.instance.checkUserSession();
                }

                if (models.UserSession.getPendingPackage() != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/viewdetails.fxml"));
                    Parent root = loader.load();
                    ViewDetailsController detCtrl = loader.getController();
                    detCtrl.initData(models.UserSession.getPendingPackage());
                    models.UserSession.cleanPendingPackage();
                    Stage stage = (Stage) EmailBox.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } 
                else if (models.UserSession.getPendingHotel() != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelViewDetails2.fxml"));
                    Parent root = loader.load();
                    HotelViewDetailsController2 hotelDetCtrl = loader.getController();
                    hotelDetCtrl.initData(models.UserSession.getPendingHotel());
                    models.UserSession.cleanPendingHotel();
                    Stage stage = (Stage) EmailBox.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } 
                else {
                    loadPage(event, "Main.fxml");
                }
            } else {
                System.out.println("❌ Invalid Email or Password!");
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    // ==================== Signup Method ====================
    @FXML
    private void handleSignup(ActionEvent event) {
        String email = signupEmail.getText();
        String password = signupPassword.getText();
        String confirm = confirmPassword.getText();

        if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            System.out.println("Please fill all fields.");
            return;
        }

        // ⭐ Admin စကားလုံးဖြင့် Register လုပ်ခြင်းကို တားဆီးခြင်း
        if (email.toLowerCase().contains("admin")) {
            System.out.println("❌ Registration Failed: 'admin' is a restricted keyword.");
            // ဤနေရာတွင် User ကို Alert Box ပြပေးလျှင် ပိုကောင်းသည်
            return;
        }

        if (!password.equals(confirm)) {
            System.out.println("Passwords do not match!");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO logtable (email, password) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Signup Successful!");
                goToSigninPage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ==================== Navigation Methods ====================
    @FXML
    private void goToSignup(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/Signup.fxml"));
            Stage stage = (Stage) EmailBox.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToSigninPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/Signin.fxml"));
            Stage stage = (Stage) signupEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadPage(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/" + fxmlFile));
            Stage stage = (Stage) EmailBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}