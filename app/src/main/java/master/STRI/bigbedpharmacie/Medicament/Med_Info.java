package master.STRI.bigbedpharmacie.Medicament;

public class Med_Info {
    private String name, Description ,id,medId;
    private boolean type;

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public boolean isType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }
    public Med_Info(){

    }
    public Med_Info(String name, String description, String id,boolean type,String medId) {
        this.name = name;
        Description = description;
        this.id = id;
        this.type=type;
        this.medId=medId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
