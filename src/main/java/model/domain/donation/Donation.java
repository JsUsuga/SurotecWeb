package model.domain.donation;

import java.time.LocalDateTime;

public class Donation {
    private int id;
    private double amount;
    private LocalDateTime donationDate;
    private int userId;
    private int foundationId;

    // Constructor, getters y setters...
    public Donation(double amount, int userId, int foundationId) {
        this.amount = amount;
        this.userId = userId;
        this.foundationId = foundationId;
        this.donationDate = LocalDateTime.now();
    }

    // Getters y Setters para todos los atributos...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    // ...etc.
}