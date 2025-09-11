package model.domain.offer;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Offer {
    protected Long id;
    protected String title;
    protected String description;
    protected LocalDateTime createdAt;

    public Offer() {
        this.createdAt = LocalDateTime.now();
    }

    public Offer(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Tipo que identifica la subclase (p.ej. "JOB" o "ACADEMIC")
     */
    public abstract String getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", type=" + getType() +
                '}';
    }
}
