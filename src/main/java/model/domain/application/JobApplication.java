package model.domain.application;

import java.time.LocalDateTime;
import java.util.Objects;

public class JobApplication {
    private Long id;
    private Long offerId;
    private Long userId;
    private LocalDateTime appliedAt;

    public JobApplication() {
        this.appliedAt = LocalDateTime.now();
    }

    public JobApplication(Long id, Long offerId, Long userId) {
        this.id = id;
        this.offerId = offerId;
        this.userId = userId;
        this.appliedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOfferId() { return offerId; }
    public void setOfferId(Long offerId) { this.offerId = offerId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobApplication)) return false;
        JobApplication that = (JobApplication) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", offerId=" + offerId +
                ", userId=" + userId +
                ", appliedAt=" + appliedAt +
                '}';
    }
}
