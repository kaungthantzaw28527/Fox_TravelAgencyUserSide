package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourPackageDAO2 {
    private final String URL = "jdbc:mysql://localhost:3306/travel_agency";
    private final String USER = "root";
    private final String PASS = "1234"; 

    public List<TourPackage> getAllPackages() {
        List<TourPackage> list = new ArrayList<>();
        String sql = "SELECT * FROM tourpackages";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
           // System.out.println("✅ Database ချိတ်ဆက်မှု အောင်မြင်သည်!"); // ချိတ်ဆက်မှုရှိမရှိ စစ်ရန်

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                int count = 0;
                while (rs.next()) {
                    list.add(new TourPackage(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("duration"),
                        rs.getString("image_path"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("description")
                    ));
                    count++;
                }
                //System.out.println("✅ ရှာဖွေတွေ့ရှိသော Data အရေအတွက်: " + count); // Data ရှိမရှိ စစ်ရန်
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}