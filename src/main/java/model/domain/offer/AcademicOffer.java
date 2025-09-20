package model.domain.offer;

public class AcademicOffer extends Offer {

    private String institution;
    private String program;

    public AcademicOffer() {
        super();
    }

    public AcademicOffer(Long id, String title, String description, String institution, String program) {
        super(id, title, description);
        this.institution = institution;
        this.program = program;
    }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    @Override
    public String getType() {
        return "ACADEMIC";
    }

    @Override
    public String toString() {
        return "AcademicOffer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", institution='" + institution + '\'' +
                ", program='" + program + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
