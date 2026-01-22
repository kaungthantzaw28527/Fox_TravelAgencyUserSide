package application;
	
import java.net.URL;
import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = getClass().getResource("Main.fxml");
			loader.setLocation(url);
			Parent root = loader.load();
			
			Scene scene = new Scene(root,1333,762);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);  
			primaryStage.show();
			Connection con = DBConnection.connect();
			if (con!=null) {
				System.out.println("Connection Successful");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
