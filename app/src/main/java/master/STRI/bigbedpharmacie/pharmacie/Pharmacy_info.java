package master.STRI.bigbedpharmacie.pharmacie;

import com.google.firebase.firestore.Exclude;

public class Pharmacy_info {

    @Exclude
    private String parmacieId;

    public String getParmacieId() {
        return parmacieId;
    }

    public void setParmacieId(String parmacieId) {
        this.parmacieId = parmacieId;
    }

    private String fullName, email, Telephone, ville;
    private double latitude, longitude;
    private boolean pstatus;

    public Pharmacy_info() {
    }

    public Pharmacy_info(String fullName, String email, String Telephone, String ville, double latitude, double longitude, boolean pstatus) {
        this.fullName = fullName;
        this.email = email;
        this.Telephone = Telephone;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pstatus = pstatus;
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

    public boolean getPstatus() {
        return pstatus;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPstatus(boolean pstatus) {
        this.pstatus = pstatus;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
