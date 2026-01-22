package controllers;

import java.net.URL;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.SearchPackageDAO;
import models.TourPackage;
import models.TourPackageDAO2;

public class Packages_Controllers {
    private TourPackageDAO2 mainDAO = new TourPackageDAO2();
    private SearchPackageDAO searchDAO = new SearchPackageDAO();
    //သက်မွန်ကုဒ်{
    @FXML private GridPane packageContainer;
    @FXML private TextField searchField;
    
    @FXML private HBox authGroup;
    @FXML private MenuButton userProfileMenu;
    @FXML private MenuItem userEmailItem;

    private void loadPage(ActionEvent event, String fxml) {
        try {
            URL url = getClass().getResource("/application/" + fxml);
            FXMLLoader loader = new FXMLLoader(url); // Loader ကို အရင်တည်ဆောက်ပါ
            Parent root = loader.load();

            // Page အသစ်ရဲ့ Controller ကို လှမ်းယူမယ်
            Object controller = loader.getController();

            // Controller အမျိုးအစားအလိုက် checkUserSession() ကို လှမ်းနှိုးပေးမယ်
            if (controller instanceof Main_Controllers) {
                ((Main_Controllers) controller).checkUserSession();
            } else if (controller instanceof UserHotel_Controllers) {
                ((UserHotel_Controllers) controller).checkUserSession();
            } else if (controller instanceof Packages_Controllers) {
                ((Packages_Controllers) controller).checkUserSession();
            } else if (controller instanceof UserBookings_Controllers) {
                ((UserBookings_Controllers) controller).checkUserSession();
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1333, 762));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    //သက်မွန်ကုဒ်
    @FXML
    public void initialize() {
        showPackages(mainDAO.getAllPackages());
        
        checkUserSession(); // UI Update လုပ်ဖို့ ခေါ်ခြင်း
    }

    public void checkUserSession() {
        String email = models.UserSession.getUserEmail();
        if (email != null) {
            if (authGroup != null) authGroup.setVisible(false);
            if (userProfileMenu != null) {
                userProfileMenu.setVisible(true);
                userEmailItem.setText(email);
            }
        } else {
            if (authGroup != null) authGroup.setVisible(true);
            if (userProfileMenu != null) userProfileMenu.setVisible(false);
        }
    }
    
    //သက်မွန်ကုဒ်
    @FXML
    public void handleSearch(ActionEvent event) {
        String query = searchField.getText();
        if (query == null || query.isEmpty()) {
            showPackages(mainDAO.getAllPackages());
        } else {
            showPackages(searchDAO.searchByCity(query));
        }
    }
    
    //သက်မွန်ကုဒ်
    private void showPackages(List<TourPackage> list) {
        if (packageContainer == null) return;
        packageContainer.getChildren().clear();
        int col = 0, row = 0;
        
        if (list != null) {
            for (TourPackage tp : list) {
                VBox card = createPackageCard(tp);
                packageContainer.add(card, col++, row);
                if (col == 3) { col = 0; row++; }
            }
        }
    }
    
    //သက်မွန်ကုဒ်
    private VBox createPackageCard(TourPackage tp) {
        VBox card = new VBox(10);
        card.setPrefSize(260, 300);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        ImageView iv = new ImageView();
        try {
            if (tp.getImagePath() != null && !tp.getImagePath().isEmpty()) {
                Image img = new Image(getClass().getResourceAsStream("/images/" + tp.getImagePath()));
                iv.setImage(img);
            }
        } catch (Exception e) {
            System.out.println("❌ Image Error: " + tp.getImagePath());
        }
        iv.setFitWidth(220); iv.setFitHeight(130); iv.setPreserveRatio(true);

        Label name = new Label(tp.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        
        Button btn = new Button();
        
        if (isCity(tp.getName())) {
            btn.setText("See More Package");
        } else {
            btn.setText("View Details");
        }
        
        btn.setOnAction(e -> {
            if (isCity(tp.getName())) {
                initData(tp.getName());
            } else {
                showDetails(tp);
            }
        });

        card.getChildren().addAll(iv, name, btn);
        return card;
    }
    //သက်မွန်ကုဒ်
    private boolean isCity(String name) {
        return name.equalsIgnoreCase("Bagan") || 
               name.equalsIgnoreCase("Ngwe Saung") || 
               name.equalsIgnoreCase("Chaung Thar") ||
               name.equalsIgnoreCase("Pyin Oo Lwin") ||
               name.equalsIgnoreCase("Mountain") ||
               name.equalsIgnoreCase("City Explorer");
    }
    
    private void showDetails(TourPackage selectedPackage) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ViewDetails.fxml"));
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/HotelViewDetails2.fxml"));
            Parent root = loader.load();
            ViewDetailsController controller = loader.getController();
            controller.initData(selectedPackage);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Details: " + selectedPackage.getName());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //သက်မွန်ကုဒ်
//    public void initData(String cityName) {
//        List<TourPackage> results = searchDAO.searchByCity(cityName);
//        showPackages(results);
//    	try {
//            List<TourPackage> packages = mainDAO.getAllPackages();
//            System.out.println("Found packages: " + packages.size()); // Console မှာ စစ်ဖို့
//            if (packageContainer == null) {
//                System.out.println("❌ Error: packageContainer is NULL. Check your FXML fx:id!");
//            }
//            showPackages(packages);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
//    public void initData(String cityName) {
//        if (cityName != null) {
//           
//            searchField.setText(cityName);
//            
//           
//            List<TourPackage> results = searchDAO.searchByCity(cityName);
//            
//            showPackages(results); 
//        }
//    }
    public void initData(String cityName) {
        if (cityName != null) {
            // searchField က null ဖြစ်နေရင် error မတက်အောင် စစ်မယ်
            if (searchField != null) {
                searchField.setText(cityName);
            } else {
                System.out.println("⚠️ Warning: searchField is null, but searching for " + cityName);
            }
            
            // Data ကိုတော့ ရှာပြီး UI မှာ ပြပေးမယ်
            List<TourPackage> results = searchDAO.searchByCity(cityName);
            showPackages(results); 
        }
    }
        

	  public void handlemainbutton(ActionEvent e) {
	  loadPage(e, "Main.fxml");
	}
	
	    public void handlehotelbutton(ActionEvent e) {
	        loadPage(e, "userHotel.fxml");
	    }
	
	public void handlepackagesbutton(ActionEvent e) {
	  loadPage(e, "Packages.fxml");
	}
	
	public void handlebookingsbutton(ActionEvent e) {
	  loadPage(e, "Bookings.fxml");
	}
	
    public void handlelogin(ActionEvent e) {
        loadPage(e, "Signin.fxml");
    }

    public void handleregister(ActionEvent e) {
        loadPage(e, "Signup.fxml");
    }
    @FXML
    private void handleSignOut(ActionEvent event) {
        models.UserSession.cleanSession();
        checkUserSession();
    }
// // ၁။ selectedPackageId ဆိုတဲ့ variable ကို class အပေါ်ဆုံးမှာ ကြေညာပါ
//    private int selectedPackageId;
//
//    /**
//     * ⭐ Parameter (၃) ခု လက်ခံဖို့ ပြင်လိုက်ပါပြီ
//     * (int packageId, String packageName, String imagePath)
//     */
//    public void initData(int packageId, String packageName, String imagePath) {
//        // ၂။ လက်ခံရရှိတဲ့ ID ကို variable ထဲမှာ သိမ်းလိုက်ပြီ (Foreign Key အတွက်)
//        this.selectedPackageId = packageId; 
//        
//        this.lblPackageName.setText(packageName);
//        
//        try {
//            if (imagePath != null && !imagePath.isEmpty()) {
//                String fullPath = imagePath.startsWith("/") ? imagePath : "/images/" + imagePath;
//                var inputStream = getClass().getResourceAsStream(fullPath);
//                if (inputStream != null) {
//                    imgTour.setImage(new Image(inputStream));
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("❌ Image loading error: " + e.getMessage());
//        }
//    }
}