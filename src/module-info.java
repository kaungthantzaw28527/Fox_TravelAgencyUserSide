module TravelAgencyUSER {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires jdk.jfr;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers to javafx.fxml;
	//opens models to javafx.base;
}
