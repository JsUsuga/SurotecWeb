package repository.donation;

import model.domain.donation.Donation;
import repository.config.DatabaseConnection; // ¡Importante! Usamos la ruta de tu proyecto

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DonationRepositoryImpl implements DonationRepository {

    @Override
    public Donation save(Donation donation) {
        String sql = "INSERT INTO donations (amount, donation_date, user_id, foundation_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect(); // Correcto según tu estructura
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, donation.getAmount());
            pstmt.setTimestamp(2, Timestamp.valueOf(donation.getDonationDate()));
            pstmt.setInt(3, donation.getUserId());
            pstmt.setInt(4, donation.getFoundationId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        donation.setId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Donación guardada en la base de datos.");
            return donation;

        } catch (SQLException e) {
            System.err.println("Error al guardar la donación: " + e.getMessage());
            e.printStackTrace(); // Es bueno ver el error completo mientras desarrollas
            return null;
        }
    }

    @Override
    public Optional<Donation> findById(int id) {
        // Implementar lógica SQL para SELECT ... WHERE id = ?
        return Optional.empty(); // Placeholder
    }

    @Override
    public List<Donation> findAllByFoundationId(int foundationId) {
        // Implementar lógica SQL para SELECT ... WHERE foundation_id = ?
        return null; // Placeholder
    }
}