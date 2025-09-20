package model.domain.factory;

import model.domain.payment.PaymentGateway; // Importamos la interfaz
import service.payment.PaypalGateway;       // Importamos la implementación
import service.payment.WompiGateway;        // Importamos la implementación

public class PaymentFactory {
    public static PaymentGateway createPaymentGateway(String gatewayType) {
        if ("WOMPI".equalsIgnoreCase(gatewayType)) {
            return new WompiGateway();
        } else if ("PAYPAL".equalsIgnoreCase(gatewayType)) {
            return new PaypalGateway();
        }
        throw new IllegalArgumentException("Tipo de pasarela de pago desconocida: " + gatewayType);
    }
}