package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import models.PackagebookingDAO;
import models.TourPackage; // TourPackage model ကို import လုပ်ထားပါ
import java.time.LocalDate;

public class HotelPackagebookingController2 {

    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtMember;   
    @FXML private DatePicker dpStartDate;
    
    @FXML private ImageView imgTour;      
    @FXML private Label lblPackageName;   

    private PackagebookingDAO bookingDAO = new PackagebookingDAO(); 


    public void initData(String packageName, String imagePath) {
        lblPackageName.setText(packageName);
        
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                String path = imagePath.trim();
                Image img = null;

                if (path.contains(":") || path.startsWith("/") || path.contains("\\")) {
                    java.io.File file = new java.io.File(path);
                    if (file.exists()) {
                        img = new Image(file.toURI().toString());
                    }
                } else {
                    String fullResourcePath = "/images/" + path;
                    var inputStream = getClass().getResourceAsStream(fullResourcePath);
                    if (inputStream != null) {
                        img = new Image(inputStream);
                    }
                }

                if (img != null) imgTour.setImage(img);
            }
        } catch (Exception e) {
            System.out.println("❌ Image loading error: " + e.getMessage());
        }
    }

    @FXML
    public void handleBook(ActionEvent event) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        LocalDate startDate = dpStartDate.getValue();
        String memberStr = txtMember.getText();

        if (name.isEmpty() || phone.isEmpty() || startDate == null || memberStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all required fields!");
            return;
        }

        try {
            int memberCount = Integer.parseInt(memberStr);
            String dateStr = startDate.toString();

            // DAO သုံးပြီး database ထဲ သိမ်းမယ်
            boolean isSaved = bookingDAO.savePackageBooking(memberCount, name, email, phone, dateStr, memberCount);

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking Saved Successfully!");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Database connection error!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Members must be a number!");
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    public void handleBack(ActionEvent event) {
        try {
            // Back ပြန်တဲ့အခါ Main Page သို့မဟုတ် ViewDetails ဆီ ပြန်ပို့ပေးပါ
            Parent root = FXMLLoader.load(getClass().getResource("/application/userHotel.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtMember.clear();
        dpStartDate.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}