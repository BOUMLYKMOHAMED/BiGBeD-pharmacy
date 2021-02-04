package master.STRI.bigbedpharmacie;

public class Pharmacy_info {
    private String fullName,email,Telephone,ville;
    private double latitude,longitude;

    public Pharmacy_info(){}

    public Pharmacy_info(String fullName,String email,String Telephone,String ville, double latitude,double longitude){
        this.fullName=fullName;
        this.email=email;
        this.Telephone=Telephone;
        this.ville=ville;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public String getEmail() {
        return email;
    }
    public String getTelephone() {
        return Telephone;
    }
    public String getFullName() {
        return fullName;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getVille() {
        return ville;
    }

    public double getLongitude() {
        return longitude;
    }
}
