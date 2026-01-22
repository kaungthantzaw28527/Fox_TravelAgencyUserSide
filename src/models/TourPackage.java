package models;

public class TourPackage {
    private int id;
    private String name, duration, imagePath, address, phone, description;
    

    public TourPackage() {}

    public TourPackage(int id, String name,  String duration, String imagePath, String address, String phone, String description) {
        this.id = id; this.name = name;  this.duration = duration;
        this.imagePath = imagePath; this.address = address; this.phone = phone; this.description = description;
    }
  
    public String getName() { return name; }  
    public String getDuration() { return duration; }
    public String getImagePath() { return imagePath; }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

	
    
    
}