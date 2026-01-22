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
import models.TourPackage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.io.IOException;

public class ViewDetailsController {
    @FXML private ImageView img1;
    @FXML private Label lblTitle, lblDescription;

    private TourPackage currentTour;

    public void initData(TourPackage tour) {
        this.currentTour = tour; 
        
        if (tour != null) {
            lblTitle.setText(tour.getName());
            
           // if (lblAddress != null) lblAddress.setText(tour.getAddress());
           // if (lblPhone != null) lblPhone.setText(tour.getPhone());
            if (lblDescription != null) lblDescription.setText(tour.getDescription());
            
           
            // if (lblPrice != null) lblPrice.setText("Price on Inquiry"); 

            if (img1 != null && tour.getImagePath() != null) {
                try {
                    String fullPath = "/images/" + tour.getImagePath();
                    Image image = new Image(getClass().getResourceAsStream(fullPath));
                    img1.setImage(image);
                } catch (Exception e) { 
                    System.out.println("❌ Image Error!"); 
                }
            }
        }
    }

//    @FXML
//    public void handleBooking(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Packagebooking.fxml"));
//            Parent root = loader.load();
//
//            PackagebookingController bookingCtrl = loader.getController();
//            if (currentTour != null) {
//                bookingCtrl.initData(currentTour.getName(), currentTour.getImagePath());
//            }
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @FXML
//    public void handleBooking(ActionEvent event) {
//        // ၁။ User Login ဝင်ထားသလား စစ်ဆေးခြင်း
//        String userEmail = models.UserSession.getUserEmail();
//
//        if (userEmail != null) {
//            // အကောင့်ဝင်ထားလျှင် ပုံမှန်အတိုင်း Booking Page သို့သွားမည်
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Packagebooking.fxml"));
//                Parent root = loader.load();
//
//                PackagebookingController bookingCtrl = loader.getController();
//                if (currentTour != null) {
//                    bookingCtrl.initData(currentTour.getName(), currentTour.getImagePath());
//                }
//
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(new Scene(root));
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // ၂။ အကောင့်မဝင်ထားလျှင် လက်ရှိ Package ကို သိမ်းပြီး Login Page ပြမည်
//            System.out.println("⚠️ Please login first!");
//            models.UserSession.setPendingPackage(currentTour); // Pending သိမ်းမယ်
//            
//            try {
//                Parent root = FXMLLoader.load(getClass().getResource("/application/Signin.fxml"));
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(new Scene(root));
//                stage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    @FXML
    public void handleBooking(ActionEvent event) {
        // ၁။ User Login ဝင်ထားသလား စစ်ဆေးခြင်း
        String userEmail = models.UserSession.getUserEmail();

        if (userEmail != null) {
            // အကောင့်ဝင်ထားလျှင် ပုံမှန်အတိုင်း Booking Page သို့သွားမည်
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Packagebooking.fxml"));
                Parent root = loader.load();

                PackagebookingController bookingCtrl = loader.getController();
                
                if (currentTour != null) {
                    // ⭐ ဒီနေရာမှာ ID ပါအောင် ဖြည့်စွက်ပေးထားပါတယ် (Foreign Key အတွက်)
                    bookingCtrl.initData(
                        currentTour.getId(),        // ၁။ Package ID (ဒါလေး အသစ်ထည့်လိုက်တာ)
                        currentTour.getName(),      // ၂။ Package Name
                        currentTour.getImagePath()   // ၃။ Image Path
                    );
                }

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // ၂။ အကောင့်မဝင်ထားလျှင် လက်ရှိ Package ကို သိမ်းပြီး Login Page ပြမည်
            System.out.println("⚠️ Please login first!");
            models.UserSession.setPendingPackage(currentTour); // Pending သိမ်းမယ်
            
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/application/Signin.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void handleBack(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}   