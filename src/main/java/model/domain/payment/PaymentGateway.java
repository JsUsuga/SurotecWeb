package model.domain.payment;

// Esta es la estrategia
public interface PaymentGateway {
    boolean processPayment(double amount);
}