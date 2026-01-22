package controllers;

import javafx.scene.control.MenuItem;
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


public class Main_Controllers {
	// လက်ရှိ ပွင့်နေတဲ့ Main Controller ကို တခြားနေရာက လှမ်းခေါ်လို့ရအောင် Static ထားပါမယ်
	public static Main_Controllers instance;

	private void loadPage(ActionEvent event, String fxml) {
	    try {
	        URL url = getClass().getResource("/application/" + fxml);
	        FXMLLoader loader = new FXMLLoader(url);
	        Parent root = loader.load();
	        Object controller = loader.getController();

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
    
    //သက်မွန်
    private TourPackageDAO2 mainDAO = new TourPackageDAO2();
    private SearchPackageDAO searchDAO = new SearchPackageDAO();
    //သက်မွန်ကုဒ်{
    @FXML private GridPane packageContainer;
    @FXML private TextField searchField;
    
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
    
    //သက်မွန်ကုဒ်
    @FXML
    public void initialize() {
        showPackages(mainDAO.getAllPackages());
        checkUserSession();
        instance = this; // Initialize လုပ်တိုင်း instance ကို သိမ်းမယ်
        showPackages(mainDAO.getAllPackages());
        checkUserSession();
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
        
        btn.setStyle("-fx-background-color: #7f7fff; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand;");
                
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
        // မြို့နာမည်စာရင်းကို စစ်ဆေးတာပါ
        return name.equalsIgnoreCase("Bagan") || 
               name.equalsIgnoreCase("Ngwe Saung") || 
               name.equalsIgnoreCase("Chaung Thar") ||
               name.equalsIgnoreCase("Pyin Oo Lwin") ||
               name.equalsIgnoreCase("Mountain") ||
               name.equalsIgnoreCase("City Explorer");
    }
    
    private void showDetails(TourPackage selectedPackage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/viewdetails.fxml"));
            Parent root = loader.load();
            ViewDetailsController controller = loader.getController();
            if (controller != null) {
                controller.initData(selectedPackage);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Details: " + selectedPackage.getName());
            stage.show();
        } catch (Exception e) {
            System.err.println("❌ View Details Page do not load!");
            e.printStackTrace();
        }
    }
    //သက်မွန်ကုဒ်
   
    public void initData(String cityName) {
        List<TourPackage> results = searchDAO.searchByCity(cityName);
        showPackages(results);
    }
    //
    @FXML private HBox authGroup;
    @FXML private MenuButton userProfileMenu;
    @FXML private MenuItem userEmailItem;
    
    public void checkUserSession() {
        String email = models.UserSession.getUserEmail();
        if (email != null) {
            authGroup.setVisible(false); // Login button များကို ဖျောက်မည်
            userProfileMenu.setVisible(true); // Profile menu ကို ပြမည်
            userEmailItem.setText(email); // User ဝင်ထားသော email ကို Menu ထဲတွင် ပြမည်
        } else {
            authGroup.setVisible(true);
            userProfileMenu.setVisible(false);
        }
    }

    @FXML
    private void handleSignOut(javafx.event.ActionEvent event) {
        models.UserSession.cleanSession(); // Session ဖျက်မည်
        checkUserSession(); // UI ကို မူလအတိုင်း (Login/Register) ပြန်ပြောင်းမည်
    }
}
