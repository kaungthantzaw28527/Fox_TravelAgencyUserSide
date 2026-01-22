package models;

public class HotelPackage {
    private int id;
    // ✅ duration variable ကို ဖယ်လိုက်ပါပြီ
    private String name, imagePath, address, phone, description;

    public HotelPackage() {}
    //
    // ✅ Constructor ထဲကနေ duration ကို ဖြုတ်လိုက်ပါပြီ
    public HotelPackage(int id, String name, String imagePath, String address, String phone, String description) {
        this.id = id; 
        this.name = name;
        this.imagePath = imagePath; 
        this.address = address; 
        this.phone = phone; 
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}