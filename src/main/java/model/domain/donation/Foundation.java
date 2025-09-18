package model.domain.donation;

public abstract class Foundation {
    protected int id;
    protected String name;
    protected String description;

    // Constructor, getters y setters... (igual que antes)
    public Foundation(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public abstract String getFoundationType();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}