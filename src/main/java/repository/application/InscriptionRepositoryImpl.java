package repository.application;

import model.domain.application.Inscription;
import repository.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionRepositoryImpl implements InscriptionRepository{

    private final DatabaseConnection databaseConnection = new DatabaseConnection();


    @Override
    public void create(Inscription inscription) {
        String sql = "INSERT INTO inscription (student_id, project_id) VALUES (?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, (int) inscription.getStudentId());
            stmt.setInt(2, (int) inscription.getProjectId());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                System.out.println("No se insertó la inscripción (0 filas afectadas).");
                return;
            }

            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    int id = gk.getInt(1);
                    inscription.setInscriptionId(id);
                    System.out.println("Inscripción creada con id = " + id);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error creando inscripción: " + ex.getMessage());
        }
    }

    @Override
    public List<Inscription> readAll() {
        List<Inscription> list = new ArrayList<>();
        String sql = "SELECT * FROM inscription";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inscription i = new Inscription(
                        rs.getInt("inscription_id"),
                        rs.getInt("student_id"),
                        rs.getInt("project_id")
                );
                list.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Error leyendo inscripciones: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public Inscription readById(int id) {
        String sql = "SELECT * FROM inscription WHERE inscription_id = ?";
        Inscription i = null;
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    i = new Inscription(
                            rs.getInt("inscription_id"),
                            rs.getInt("student_id"),
                            rs.getInt("project_id")
                    );
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error leyendo inscripción por id: " + ex.getMessage());
        }
        return i;
    }

    @Override
    public List<Inscription> readByStudentId(int studentId) {
        List<Inscription> list = new ArrayList<>();
        String sql = "SELECT * FROM inscription WHERE student_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inscription i = new Inscription(
                            rs.getInt("inscription_id"),
                            rs.getInt("student_id"),
                            rs.getInt("project_id")
                    );
                    list.add(i);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error leyendo inscripciones por studentId: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public List<Inscription> readByProjectId(int projectId) {
        List<Inscription> list = new ArrayList<>();
        String sql = "SELECT * FROM inscription WHERE project_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inscription i = new Inscription(
                            rs.getInt("inscription_id"),
                            rs.getInt("student_id"),
                            rs.getInt("project_id")
                    );
                    list.add(i);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error leyendo inscripciones por projectId: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM inscription WHERE inscription_id = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Inscripción eliminada." : "Inscripción no encontrada.");
        } catch (SQLException ex) {
            System.out.println("Error eliminando inscripción: " + ex.getMessage());
        }
    }
}
