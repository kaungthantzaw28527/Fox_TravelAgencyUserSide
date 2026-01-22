package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewDetailsDAO {
    private final String URL = "jdbc:mysql://localhost:3306/travel_agency"; 
    private final String USER = "root";
    private final String PASS = "1234"; 

    public TourPackage getTourByName(String name) {
        // ⭐ SQL query မှာ column နာမည်တွေ မှန်အောင် စစ်ထားပါတယ်
        String query = "SELECT * FROM tourpackages WHERE name = ?"; 
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = con.prepareStatement(query)) {
            
            pstmt.setString(1, name); 
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // ⭐ TourPackage model ရဲ့ parameter ၈ ခုပါတဲ့ constructor ကို သုံးလိုက်ပါမယ်
                // image_path ကို အသေးနဲ့ ပြန်ပြင်ပေးထားတယ်နော် (rs.getString("image_path"))
                return new TourPackage(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("duration"),
                    rs.getString("image_path"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("description")
                );
            }
        } catch (Exception e) {
            System.out.println("ViewDetailsDAO Error!");
            e.printStackTrace();
        }
        return null;
    }
}