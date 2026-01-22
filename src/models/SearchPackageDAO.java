package models;

import java.sql.*;
import java.util.*;

public class SearchPackageDAO {
    private final String URL = "jdbc:mysql://localhost:3306/travel_agency";
    private final String USER = "root";
    private final String PASS = "1234";

    public List<TourPackage> searchByCity(String cityName) {
        List<TourPackage> list = new ArrayList<>();
        
        String sql = "SELECT cl.* FROM city_locations cl " +
                     "JOIN tourpackages tp ON cl.tourpackage_id = tp.id " +
                     "WHERE tp.name LIKE ?";
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + cityName + "%");
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                TourPackage tp = new TourPackage();
                tp.setId(rs.getInt("id"));
                tp.setName(rs.getString("location_name"));
                tp.setDuration(rs.getString("duration"));
                tp.setImagePath(rs.getString("image_path"));
                tp.setDescription(rs.getString("description"));
                
                list.add(tp);
            }
        } catch (Exception e) { 
            System.out.println("❌ Search Error: " + e.getMessage());
            e.printStackTrace(); 
        }
        return list;
    }
}