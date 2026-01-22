package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.HotelPackage; 
import models.HotelSearchPackageDAO2; // ⭐ DAO နာမည်အသစ်ကို import လုပ်ပါ
import java.util.List;

public class HotelSearchPackageController2 {
    @FXML private TextField searchField;
    @FXML private GridPane packageContainer;
    
   
    private HotelSearchPackageDAO2 dao = new HotelSearchPackageDAO2();

    @FXML
    public void initialize() {
        handleSearch();
    }

    @FXML
    public void handleSearch() {
        String text = (searchField != null) ? searchField.getText() : "";
      
        List<HotelPackage> list = dao.searchHotelsByCity(text); 
        display(list); 
    }

    private void display(List<HotelPackage> list) {
        if (packageContainer == null) return;
        packageContainer.getChildren().clear();
        
        int col = 0, row = 0;
        if (list != null) {
            for (HotelPackage hp : list) {
                VBox card = createCard(hp);
                packageContainer.add(card, col, row);
                
                col++;
                if (col == 3) { 
                    col = 0;
                    row++;
                }
            }
        }
    }

    private VBox createCard(HotelPackage hp) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setPrefSize(250, 300);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                     "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        Label name = new Label(hp.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Button btn = new Button("View Details");
        btn.setStyle("-fx-background-color: #7f7fff; -fx-text-fill: white; -fx-background-radius: 10;");
        
        btn.setOnAction(e -> {
            try {
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelViewDetails2.fxml"));
                Parent root = loader.load();
                
                
                HotelViewDetailsController2 detailCtrl = loader.getController();
                
             
                detailCtrl.initData(hp); 

                Stage stage = (Stage) btn.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Hotel Details: " + hp.getName());
                stage.show();
            } catch (Exception ex) { 
                ex.printStackTrace(); 
            }
        });

        card.getChildren().addAll(name, btn);
        return card;
    }
}