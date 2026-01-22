package models;

import java.sql.*;
import java.util.*;

import application.DBConnection;

public class HotelSearchPackageDAO2 {
    private final String URL = "jdbc:mysql://localhost:3306/travel_agency"; 
    private final String USER = "root";
    private final String PASS = "1234"; 

//    public List<HotelPackage> searchHotelsByCity(String cityName) {
//        List<HotelPackage> list = new ArrayList<>();
//        
//        // ⭐ Foreign Key ချိတ်ထားတဲ့အတွက် JOIN သုံးပြီး ရှာပါမယ်
//        // tp.name က 'Bagan' ဖြစ်နေတဲ့ hotel_packages တွေနဲ့ သက်ဆိုင်တဲ့ cl (detail) တွေကို ယူမှာပါ
//        String sql = "SELECT cl.* FROM hoteldetailpackages cl " +
//                     "JOIN hotel_packages tp ON cl.hotelPackage_id = tp.id " +
//                     "WHERE tp.name LIKE ?";
//        
//        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
//             PreparedStatement pstmt = con.prepareStatement(sql)) {
//            
//            pstmt.setString(1, "%" + cityName + "%");
//            ResultSet rs = pstmt.executeQuery();
//            
//            while (rs.next()) {
//                HotelPackage hp = new HotelPackage();
//                hp.setId(rs.getInt("id"));
//                hp.setName(rs.getString("hotelName")); 
//                hp.setDuration(rs.getString("duration"));
//                hp.setImagePath(rs.getString("image_path"));
//                hp.setDescription(rs.getString("description"));
//                
//                list.add(hp);
//            }
//            System.out.println("✅ Found " + list.size() + " hotels with JOIN.");
//            
//        } catch (Exception e) { 
//            System.out.println("❌ JOIN Error: " + e.getMessage());
//            e.printStackTrace(); 
//        }
//        return list;
//    }
    
    public List<HotelPackage> searchHotelsByCity(String cityName) {
        List<HotelPackage> list = new ArrayList<>();
        
        // Admin ရဲ့ hotel table ထဲက hotel_location မှာ ရှာဖွေခြင်း
        String sql = "SELECT * FROM hotel WHERE hotel_location LIKE ?";
        
        try (Connection con = DBConnection.connect();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + cityName + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Duration မပါသော HotelPackage constructor အသစ်ကို သုံးထားသည်
                HotelPackage hp = new HotelPackage(
                    rs.getInt("hotel_id"),
                    rs.getString("hotel_name"),
                    rs.getString("photo_path"),
                    rs.getString("hotel_location"),
                    rs.getString("phone_number"),
                    rs.getString("description")
                );
                list.add(hp);
            }
            System.out.println("✅ Found " + list.size() + " hotels in " + cityName);
            
        } catch (Exception e) { 
            System.out.println("❌ Search Error: " + e.getMessage());
            e.printStackTrace(); 
        }
        return list;
    }
}