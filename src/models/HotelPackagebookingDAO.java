package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.DBConnection;

public class HotelPackagebookingDAO {

    // Method Signature က HotelPackagebooking ဖြစ်ရပါမယ်
    public boolean savePackageBooking(HotelPackagebooking booking) throws ClassNotFoundException {
    	String sql = "INSERT INTO hotel_reservations (customer_name, hotel_id, category_name, check_in, check_out, status, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, booking.getUserName());
            pst.setString(2, booking.getHotelName());
            pst.setString(3, booking.getCategory());
            
            // LocalDate ကို SQL Date ပြောင်းလဲခြင်း
            pst.setDate(4, java.sql.Date.valueOf(booking.getCheckIn()));
            pst.setDate(5, java.sql.Date.valueOf(booking.getCheckOut()));
            
            pst.setString(6, booking.getPhone());
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}