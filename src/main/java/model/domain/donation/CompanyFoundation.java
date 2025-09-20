package model.domain.donation;

public class CompanyFoundation extends Foundation {
    private String businessSector;

    public CompanyFoundation(int id, String name, String description, String businessSector) {
        super(id, name, description);
        this.businessSector = businessSector;
    }

    @Override
    public String getFoundationType() {
        return "Empresarial";
    }

    // Getters y setters para businessSector...
}