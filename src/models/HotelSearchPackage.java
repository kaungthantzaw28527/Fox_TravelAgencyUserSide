package models;	
public class HotelSearchPackage {
    private int id;
   // private String cityName;     // city_name အတွက်
    private String locationName; // location_name အတွက်
   // private double price;
    private String duration;
    private String imagePath;
    private String description;
    private int tourpackage_id;

    // Constructor (အစဉ်လိုက်အတိုင်း သေချာထည့်ပေးပါ)
    public HotelSearchPackage(int id, String locationName,  String duration, String imagePath, String description,int tourpackage_id) {
        this.id = id;
       // this.cityName = cityName;
        this.locationName = locationName;
        //this.price = price;
        this.duration = duration;
        this.imagePath = imagePath;
        this.description = description;
        this.tourpackage_id=tourpackage_id;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTourpackage_id() {
		return tourpackage_id;
	}

	public void setTourpackage_id(int tourpackage_id) {
		this.tourpackage_id = tourpackage_id;
	}

    
    
}