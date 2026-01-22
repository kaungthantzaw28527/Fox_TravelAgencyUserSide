package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.DBConnection;

public class HotelViewDetailsDAO2 {
    private final String URL = "jdbc:mysql://localhost:3306/travel_agency"; 
    private final String USER = "root";
    private final String PASS = "1234";  

//    public HotelPackage getHotelByName(String name) {
//        // ⭐ Table နာမည်ကို hoteldetailpackages လို့ ပြင်လိုက်ပါတယ်
//        // ⭐ Column နာမည်ကိုလည်း hotelName လို့ ပြင်ပေးထားတယ်နော်
//        String query = "SELECT * FROM hoteldetailpackages WHERE hotelName = ?"; 
//        
//        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
//             PreparedStatement pstmt = con.prepareStatement(query)) {
//            
//            pstmt.setString(1, name); 
//            ResultSet rs = pstmt.executeQuery();
//            
//            if (rs.next()) {
//                HotelPackage hp = new HotelPackage();
//                // ⭐ rs.getString() ထဲက နာမည်တွေက Database table ထဲကအတိုင်း ဖြစ်ရမယ်
//                hp.setId(rs.getInt("id"));
//                hp.setName(rs.getString("hotelName")); // database မှာ hotelName လို့ ရေးထားလို့ပါ
//                hp.setDuration(rs.getString("duration"));
//                hp.setImagePath(rs.getString("image_path"));
//                
//                // ⭐ database ပုံထဲမှာ address နဲ့ phone မပါသေးပေမဲ့ ရှိခဲ့ရင် ယူဖို့ ထည့်ထားပေးတယ်နော်
//                // hp.setAddress(rs.getString("address")); 
//                // hp.setPhone(rs.getString("phone"));
//                
//                hp.setDescription(rs.getString("description"));
//                
//                return hp;
//            }
//        } catch (Exception e) {
//            System.out.println("❌ HotelViewDetailsDAO Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
    public HotelPackage getHotelByName(String name) {
        // Admin table ဖြစ်သော 'hotel' ကို သုံးပါသည်
        String query = "SELECT * FROM hotel WHERE hotel_name = ?"; 
        
        try (Connection con = DBConnection.connect();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            
            pstmt.setString(1, name); 
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Duration ကို ဖြုတ်ထားသော Constructor ဖြင့် Map လုပ်ခြင်း
                return new HotelPackage(
                    rs.getInt("hotel_id"),
                    rs.getString("hotel_name"),
                    rs.getString("photo_path"),
                    rs.getString("hotel_location"),
                    rs.getString("phone_number"),
                    rs.getString("description")
                );
            }
        } catch (Exception e) {
            System.out.println("❌ HotelViewDetailsDAO Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}