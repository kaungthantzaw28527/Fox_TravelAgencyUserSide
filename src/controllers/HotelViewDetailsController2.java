package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.HotelPackage;

import java.io.File;

import controllers.HotelPackagebookingController2;
import javafx.event.ActionEvent;

public class HotelViewDetailsController2 {
    @FXML private ImageView img1;
    @FXML private Label lblTitle, lblAddress, lblPhone, lblDescription;

    private HotelPackage currentPackage;

    // HotelPackageController ကနေ Data လှမ်းပို့ရင် ဒါက လက်ခံမှာပါ
    public void initData(HotelPackage hotel) {
        this.currentPackage = hotel; 
        if (hotel != null) {
            if (lblTitle != null) lblTitle.setText(hotel.getName());
            if (lblAddress != null) lblAddress.setText(hotel.getAddress());
            if (lblPhone != null) lblPhone.setText(hotel.getPhone());
            if (lblDescription != null) lblDescription.setText(hotel.getDescription());

            if (img1 != null && hotel.getImagePath() != null) {
                try {
                    String path = hotel.getImagePath().trim();
                    
                    // ၁။ လမ်းကြောင်းက Local Path (C:\ သို့မဟုတ် folder path) ဖြစ်နေရင်
                    if (path.contains(":") || path.startsWith("/") || path.contains("\\")) {
                        File file = new File(path);
                        if (file.exists()) {
                            img1.setImage(new Image(file.toURI().toString()));
                        } else {
                            System.out.println("⚠️ File path exists but file not found: " + path);
                        }
                    } 
                    // ၂။ ပရောဂျက်ထဲက resources/images/ ထဲက ပုံဖြစ်နေရင်
                    else {
                        String resourcePath = "/images/" + path;
                        var stream = getClass().getResourceAsStream(resourcePath);
                        if (stream != null) {
                            img1.setImage(new Image(stream));
                        } else {
                            System.out.println("⚠️ Resource not found: " + resourcePath);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("❌ Image Error in initData: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    public void handleBooking(ActionEvent event) {
        // ၁။ Login ဝင်ထားလား စစ်ဆေးမယ်
        String email = models.UserSession.getUserEmail();

        if (email != null) {
            // အကောင့်ဝင်ထားရင် ပုံမှန်အတိုင်း Booking Page သို့သွားမယ်
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelPackagebooking2.fxml"));
                Parent root = loader.load();
                HotelPackagebookingController2 bookingCtrl = loader.getController(); 
                if (currentPackage != null && bookingCtrl != null) {
                    bookingCtrl.initData(currentPackage.getName(), currentPackage.getImagePath());
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            // ၂။ အကောင့်မဝင်ထားရင် လက်ရှိ Hotel ကို Pending သိမ်းပြီး Signin Page ကို ပို့မယ်
            models.UserSession.setPendingHotel(currentPackage);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/application/Signin.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @FXML
    public void handleBack(ActionEvent event) {
        try {
            // Hotel List Page ကို ပြန်သွားမယ်
            Parent root = FXMLLoader.load(getClass().getResource("/application/userHotel.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}