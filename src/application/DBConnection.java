package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection connect() throws ClassNotFoundException{
		Connection con = null;
		String dbname = "travel_agency";
		String username = "root";
		String password = "1234";
		//load driver
		Class.forName("com.mysql.cj.jdbc.Driver");
//		Class.forName("com.mysql.jdbc.Driver");
		try {
			//connect to db
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel_agency",username,password);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return con;
	}

	public static Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}
}
