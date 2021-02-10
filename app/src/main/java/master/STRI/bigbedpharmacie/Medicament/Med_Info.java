package master.STRI.bigbedpharmacie.Medicament;

public class Med_Info {
    private String name, Description ,id;


    public Med_Info(){

    }
    public Med_Info(String name, String description, String id) {
        this.name = name;
        Description = description;
        this.id = id;
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
