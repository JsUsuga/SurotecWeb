package service.donation;

import model.domain.donation.Donation;
import model.domain.factory.PaymentFactory;
import model.domain.payment.PaymentGateway;
import repository.donation.DonationRepository;
import repository.donation.DonationRepositoryImpl; // Importamos la implementación por ahora

public class DonationService {

    private final DonationRepository donationRepository;

    // Usamos Inyección de Dependencias. El servicio no crea el repositorio, lo recibe.
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation makeDonation(double amount, int userId, int foundationId, String paymentGatewayType) {
        if (amount <= 0) {
            System.err.println("El monto de la donación debe ser positivo.");
            return null;
        }

        // Usamos la fábrica para obtener la pasarela de pago
        PaymentGateway paymentGateway = PaymentFactory.createPaymentGateway(paymentGatewayType);

        // Procesamos el pago
        boolean paymentSuccess = paymentGateway.processPayment(amount);

        // Si el pago es exitoso, guardamos la donación usando el repositorio
        if (paymentSuccess) {
            Donation newDonation = new Donation(amount, userId, foundationId);
            return donationRepository.save(newDonation);
        } else {
            System.err.println("El pago falló. La donación no fue registrada.");
            return null;
        }
    }

    // Método principal para probar todo junto
    public static void main(String[] args) {
        // 1. Creamos una instancia del Repositorio
        DonationRepository repo = new DonationRepositoryImpl();

        // 2. Creamos una instancia del Servicio y le pasamos el repositorio
        DonationService service = new DonationService(repo);

        // 3. Hacemos una donación de prueba
        System.out.println("Intentando hacer una donación con WOMPI...");
        Donation successfulDonation = service.makeDonation(50000.0, 1, 101, "WOMPI");

        if (successfulDonation != null) {
            System.out.println("¡Donación realizada con éxito! ID de la donación: " + successfulDonation.getId());
        } else {
            System.out.println("La donación no se pudo completar.");
        }
    }
}