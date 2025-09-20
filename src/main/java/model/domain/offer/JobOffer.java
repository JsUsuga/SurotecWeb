package model.domain.offer;

public class JobOffer extends Offer {

    private String location;
    private Double salary;

    public JobOffer() {
        super();
    }

    public JobOffer(Long id, String title, String description, String location, Double salary) {
        super(id, title, description);
        this.location = location;
        this.salary = salary;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    @Override
    public String getType() {
        return "JOB";
    }

    @Override
    public String toString() {
        return "JobOffer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", salary=" + salary +
                ", createdAt=" + createdAt +
                '}';
    }
}
