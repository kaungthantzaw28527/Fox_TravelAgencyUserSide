package controllers;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserBookings_Controllers {

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
    
    @FXML private HBox authGroup;
    @FXML private MenuButton userProfileMenu;
    @FXML private MenuItem userEmailItem;
    
    @FXML
    public void initialize() {
        // လက်ရှိ page ရဲ့ တခြား logic များ...
        
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
    
    @FXML
    private void handleSignOut(ActionEvent event) {
        models.UserSession.cleanSession();
        checkUserSession();
    }
}
