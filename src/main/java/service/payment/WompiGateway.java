package service.payment;

import model.domain.payment.PaymentGateway;

public class WompiGateway implements PaymentGateway {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Procesando $" + amount + " con Wompi... ¡Pago exitoso!");
        return true;
    }
}