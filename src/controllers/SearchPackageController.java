package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.*;
import java.util.List;

public class SearchPackageController {
    @FXML private TextField searchField;
    @FXML private GridPane packageContainer;
    private SearchPackageDAO dao = new SearchPackageDAO();

    @FXML
    public void initialize() {
        
        handleSearch();
    }

    @FXML
    public void handleSearch() {
        String text = (searchField != null) ? searchField.getText() : "";
        // DAO ထဲက data တွေကို ယူမယ်
        List<TourPackage> list = dao.searchByCity(text);
        // ⭐ အောက်က display method ကို လှမ်းခေါ်တာပါ
        display(list); 
    }

    // ⭐ ဒီ method နာမည်နဲ့ အပေါ်က ခေါ်တဲ့နာမည် တူနေဖို့ လိုပါတယ်
    private void display(List<TourPackage> list) {
        if (packageContainer == null) return;
        packageContainer.getChildren().clear();
        
        int col = 0, row = 0;
        for (TourPackage tp : list) {
            VBox card = createCard(tp);
            packageContainer.add(card, col++, row);
            if (col == 2) { // ၃ ခု ပြည့်ရင် နောက်တစ်တန်းဆင်းမယ်
                col = 0; 
                row++; 
            } else {
                col++;
            }
        }
    }

    private VBox createCard(TourPackage tp) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

        Label name = new Label(tp.getName());
        name.setStyle("-fx-font-weight: bold;");

        Button btn = new Button("View Details");
        btn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ViewDetails.fxml"));
               // FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewDetails.fxml"));

                Parent root = loader.load();
                ViewDetailsController detailCtrl = loader.getController();
                detailCtrl.initData(tp); 

                Stage stage = (Stage) btn.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        card.getChildren().addAll(name, btn);
        return card;
    }
}