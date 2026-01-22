package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; // 🚩 ဒါလေး Import မလုပ်ရင် Error တက်ပါတယ်

public class PackagebookingDAO {
    
  
    private final String URL = "jdbc:mysql://localhost:3306/travel_agency";
    private final String USER = "root";
    private final String PASS = "1234"; // 

    public boolean savePackageBooking(int packageId, String name, String email, String phone, String date, int members) {
        
      String sql = "INSERT INTO package_bookings (user_name, email, address, phone, booking_date, start_date, member, package) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, name);      // user_name
            pstmt.setString(2, email);     // email
            pstmt.setString(3, "");        // address
            pstmt.setString(4, phone);     // phone
            // 🚩 index ၅ မှာ start_date ကို ထည့်ပါ (SQL ထဲမှာ booking_date က CURRENT_TIMESTAMP မိုလို index ကျော်သွားပါမယ်)
            pstmt.setString(5, date);      // start_date
            pstmt.setInt(6, members);      // member
            pstmt.setInt(7, packageId);    // package
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { // 🚩 SQLException ကို Import လုပ်ထားမှ သိမှာပါ
            System.out.println("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}