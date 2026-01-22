package models;

public class UserSession {
    private static String userEmail = null;
    
    // Tour Package အတွက် Pending သိမ်းဆည်းရန်
    private static TourPackage pendingPackage = null;
    
    // Hotel Package အတွက် Pending သိမ်းဆည်းရန် (အသစ်ထည့်သွင်းထားခြင်း)
    private static HotelPackage pendingHotel = null;

    // ==================== Email Session Management ====================
    public static void setSession(String email) {
        userEmail = email;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    // ==================== Pending Tour Package Management ====================
    public static void setPendingPackage(TourPackage tp) {
        pendingPackage = tp;
    }

    public static TourPackage getPendingPackage() {
        return pendingPackage;
    }

    public static void cleanPendingPackage() {
        pendingPackage = null;
    }
    
    // ==================== Pending Hotel Package Management ====================
    public static void setPendingHotel(HotelPackage hp) {
        pendingHotel = hp;
    }

    public static HotelPackage getPendingHotel() {
        return pendingHotel;
    }

    public static void cleanPendingHotel() {
        pendingHotel = null;
    }

    // ==================== Global Session Cleaning ====================
    // Logout လုပ်လျှင် သို့မဟုတ် အချက်အလက်အားလုံးကို ရှင်းထုတ်ရန်
    public static void cleanSession() {
        userEmail = null;
        pendingPackage = null;
        pendingHotel = null;
    }
}