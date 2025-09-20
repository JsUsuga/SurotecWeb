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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoundationId() {
        return foundationId;
    }

    public void setFoundationId(int foundationId) {
        this.foundationId = foundationId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    // ...etc.
}