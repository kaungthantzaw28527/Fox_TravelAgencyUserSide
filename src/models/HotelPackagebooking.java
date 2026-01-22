package models;

import java.time.LocalDate;

public class HotelPackagebooking {
    private String userName;
    private String hotelName;
    private String category;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String phone;

    public HotelPackagebooking(String userName, String hotelName, String category, LocalDate checkIn, LocalDate checkOut, String phone) {
        this.userName = userName;
        this.hotelName = hotelName;
        this.category = category;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.phone = phone;
    }

    // Getters and Setters
    public String getUserName() { return userName; }
    public String getHotelName() { return hotelName; }
    public String getCategory() { return category; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public String getPhone() { return phone; }
}