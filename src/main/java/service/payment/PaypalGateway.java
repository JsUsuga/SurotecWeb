package service.payment;

import model.domain.payment.PaymentGateway;

public class PaypalGateway implements PaymentGateway {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Procesando $" + amount + " con PayPal... ¡Pago exitoso!");
        return true;
    }
}