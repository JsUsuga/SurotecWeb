package model.domain.donation;

public class AcademicFoundation extends Foundation {
    private String universityAffiliation;

    public AcademicFoundation(int id, String name, String description, String universityAffiliation) {
        super(id, name, description);
        this.universityAffiliation = universityAffiliation;
    }

    @Override
    public String getFoundationType() {
        return "Académica";
    }

    // Getters y setters para universityAffiliation...
}