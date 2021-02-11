package master.STRI.bigbedpharmacie.Medicament;

public class Med_Info {
    private String name, Description ,id;
    private boolean type;

    public boolean isType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }
    public Med_Info(){

    }
    public Med_Info(String name, String description, String id,boolean type) {
        this.name = name;
        Description = description;
        this.id = id;
        this.type=type;
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
