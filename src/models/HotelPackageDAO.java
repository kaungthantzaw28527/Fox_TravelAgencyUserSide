package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import application.DBConnection;

public class HotelPackageDAO {

    public List<HotelPackage> getAllHotels() {
        List<HotelPackage> list = new ArrayList<>();
        // ✅ duration ကို query ထဲက ဖယ်လိုက်ပါပြီ
        String sql = "SELECT hotel_id, hotel_name, photo_path, hotel_location, phone_number, description FROM hotel"; 

        try (Connection con = DBConnection.connect(); 
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new HotelPackage(
                    rs.getInt("hotel_id"),
                    rs.getString("hotel_name"),
                    rs.getString("photo_path"),
                    rs.getString("hotel_location"),
                    rs.getString("phone_number"),
                    rs.getString("description")
                ));
            }
        } catch (Exception e) {
             System.out.println("❌ User Side DAO Error!");
             e.printStackTrace();
        }
        return list;
    }
}