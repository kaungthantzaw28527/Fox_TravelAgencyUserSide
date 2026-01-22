package controllers;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import controllers.HotelViewDetailsController2;
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
import models.HotelPackage;
import models.HotelPackageDAO;
import models.SearchPackageDAO;
import models.TourPackage;
import models.TourPackageDAO2;

public class UserHotel_Controllers {

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
    @FXML private GridPane packageContainer;
    @FXML private TextField searchField; 

    private HotelPackageDAO hotelDAO = new HotelPackageDAO();
    private List<HotelPackage> allHotels; 

    @FXML
    public void initialize() {
        allHotels = hotelDAO.getAllHotels();
        showPackages(allHotels);
        
        checkUserSession();
    }

    
    @FXML
    public void handleSearch(ActionEvent event) {
        String query = searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            showPackages(allHotels);
        } else {
            List<HotelPackage> filteredList = allHotels.stream()
                .filter(h -> h.getName().toLowerCase().contains(query) || 
                             h.getAddress().toLowerCase().contains(query))
                .collect(Collectors.toList());
            showPackages(filteredList);
        }
    }

    public void refreshView() {
        allHotels = hotelDAO.getAllHotels();
        showPackages(allHotels);
    }

    private void showPackages(List<HotelPackage> list) {
        if (packageContainer == null) return;
        packageContainer.getChildren().clear();
        int col = 0, row = 0;
        
        if (list != null && !list.isEmpty()) {
            for (HotelPackage hp : list) {
                VBox card = createPackageCard(hp);
                packageContainer.add(card, col++, row);
                if (col == 3) { col = 0; row++; }
            }
        }
    }

    private VBox createPackageCard(HotelPackage hp) {
        VBox card = new VBox(12);
        card.setPrefSize(260, 320);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        ImageView iv = new ImageView();
        try {
            String path = hp.getImagePath();
            if (path != null && !path.isEmpty()) {
                path = path.trim();
                
                // ၁။ လမ်းကြောင်းက C:\ ဒါမှမဟုတ် Local Path ဖြစ်နေရင် (Admin ထည့်ထားတဲ့ပုံ)
                if (path.contains(":") || path.startsWith("/") || path.contains("\\")) {
                    iv.setImage(new Image("file:" + path));
                } 
                // ၂။ ပရောဂျက်ထဲက /images/ folder ထဲက ပုံဖြစ်နေရင်
                else {
                    URL imgUrl = getClass().getResource("/images/" + path);
                    if (imgUrl != null) {
                        iv.setImage(new Image(imgUrl.toExternalForm()));
                    } else {
                        System.out.println("⚠️ Resource not found: " + path);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Image Error: " + hp.getName() + " - " + e.getMessage());
        }
        
        iv.setFitWidth(220); 
        iv.setFitHeight(140); 
        iv.setPreserveRatio(true);

        Label name = new Label(hp.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Button btn = new Button("View Details");
        btn.setStyle("-fx-background-color: #7f7fff; -fx-text-fill: white; -fx-background-radius: 20; -fx-cursor: hand;");
        
        btn.setOnAction(e -> openDetailsPage(hp, e));

        card.getChildren().addAll(iv, name, btn);
        return card;
    }
    
    private void openDetailsPage(HotelPackage hp, ActionEvent event) {
        try {
            // ⭐ လမ်းကြောင်းကို URL အဖြစ်အရင်စစ်ဆေးမယ် (Location is not set error အတွက်)
            URL fxmlLocation = getClass().getResource("/application/HotelViewDetails2.fxml");
            
            if (fxmlLocation == null) {
                // တကယ်လို့ ရှာမတွေ့ရင် ဒီနေရာမှာ Error Message ပေါ်လာပါလိမ့်မယ်
                System.err.println("❌ Error: /application/HotelViewDetails2.fxml ကို ရှာမတွေ့ပါ။ ဖိုင်နာမည် မှန်/မမှန် ပြန်စစ်ပေးပါဘေဘီလေး။");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            
            HotelViewDetailsController2 controller = loader.getController();
            if (controller != null) {
                controller.initData(hp); 
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("❌ FXMLLoader Load Error တက်နေပါတယ်!");
            e.printStackTrace();
        }
    }
    //K
    
    @FXML private HBox authGroup;
    @FXML private MenuButton userProfileMenu;
    @FXML private MenuItem userEmailItem;

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
    
    @FXML
    private void handleSignOut(ActionEvent event) {
        models.UserSession.cleanSession();
        checkUserSession();
    }
}
