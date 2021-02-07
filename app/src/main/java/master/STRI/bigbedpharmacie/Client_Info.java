package master.STRI.bigbedpharmacie;

public class Client_Info {

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
