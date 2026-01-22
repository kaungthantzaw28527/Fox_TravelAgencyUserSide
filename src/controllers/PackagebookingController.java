package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import models.PackagebookingDAO;
import java.time.LocalDate;

public class PackagebookingController {

    @FXML private TextField txtName, txtEmail, txtPhone, txtMember;
    @FXML private DatePicker dpStartDate;
    @FXML private ImageView imgTour;
    @FXML private Label lblPackageName;

    private PackagebookingDAO bookingDAO = new PackagebookingDAO();
    
    // ⭐ Foreign Key အတွက် ID သိမ်းထားဖို့ Variable (ဒါလေးရှိမှ error မတက်မှာပါ)
    private int selectedPackageId;

    /**
     * ⭐ ViewDetailsController က (int, String, String) ပို့တဲ့အတွက်
     * ဒီမှာလည်း လက်ခံတဲ့ Parameter (၃) ခု ဖြစ်အောင် ပြင်လိုက်ပါပြီ
     */
    public void initData(int packageId, String packageName, String imagePath) {
        this.selectedPackageId = packageId; // ID ကို လက်ခံသိမ်းဆည်းမယ်
        this.lblPackageName.setText(packageName);
        
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                String fullPath = imagePath.startsWith("/") ? imagePath : "/images/" + imagePath;
                var inputStream = getClass().getResourceAsStream(fullPath);
                if (inputStream != null) {
                    imgTour.setImage(new Image(inputStream));
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Image loading error: " + e.getMessage());
        }
    }

    @FXML
    public void handleBook(ActionEvent event) {
        try {
            int memberCount = Integer.parseInt(txtMember.getText());
            String dateStr = dpStartDate.getValue().toString();

            // ⭐ DAO ဆီကို selectedPackageId ပါအောင် ပို့လိုက်ပါပြီ
            boolean isSaved = bookingDAO.savePackageBooking(
                selectedPackageId, 
                txtName.getText(), 
                txtEmail.getText(), 
                txtPhone.getText(), 
                dateStr, 
                memberCount
            );

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking Saved!");
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Check Database Connection!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please check your inputs!");
        }
    }

    @FXML
    public void handleBack(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}