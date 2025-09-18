package repository.donation;

import model.domain.donation.Donation;
import java.util.List;
import java.util.Optional;

public interface DonationRepository {
    Donation save(Donation donation);
    Optional<Donation> findById(int id);
    List<Donation> findAllByFoundationId(int foundationId);
}