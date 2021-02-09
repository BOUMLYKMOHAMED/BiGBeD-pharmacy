package master.STRI.bigbedpharmacie;

import com.google.firebase.firestore.Exclude;

public class Client_Info {


    @Exclude private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String fullName,email,Telephone;
    public Client_Info(){}
    public Client_Info(String fullName,String email,String Telephone){
        this.fullName=fullName;
        this.email=email;
        this.Telephone=Telephone;
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
}
