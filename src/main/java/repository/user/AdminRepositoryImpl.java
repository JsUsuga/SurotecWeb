package repository.user;

import model.domain.user.Administrator;
import model.domain.classification.Role;
import repository.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {
    private final DatabaseConnection dbConnection = new DatabaseConnection();

    @Override
    public void create(Administrator admin) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO admin (first_name, last_name, username, password, email, is_active) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, admin.getFirstName());
            ps.setString(2, admin.getLastName());
            ps.setString(3, admin.getUsername());
            ps.setString(4, admin.getPassword());
            ps.setString(5, admin.getEmail());
            ps.setBoolean(6, true);
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    admin.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public List<Administrator> readAll() {
        List<Administrator> admins = new ArrayList<>();
        try (Connection conn = dbConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT a.*, r.role_name FROM admin a LEFT JOIN admin_role r ON a.user_id = r.user_id")) {

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                boolean isActive = rs.getBoolean("is_active");
                String roleName = rs.getString("role_name");
                Role role = roleName != null ? Role.valueOf(roleName) : Role.PUBLISHER_OF_ACADEMIC_OFFERS;

                Administrator admin = new Administrator(id, firstName, lastName, username, password, email, role);
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
        return admins;
    }

    @Override
    public Administrator readById(int id) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT a.*, r.role_name FROM admin a LEFT JOIN admin_role r ON a.user_id = r.user_id WHERE a.user_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    boolean isActive = rs.getBoolean("is_active");
                    String roleName = rs.getString("role_name");
                    Role role = roleName != null ? Role.valueOf(roleName) : Role.PUBLISHER_OF_ACADEMIC_OFFERS;

                    return new Administrator(userId, firstName, lastName, username, password, email, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Administrator findByUserId(int userId) {
        return readById(userId);
    }

    @Override
    public Administrator findByUsername(String username) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT a.*, r.role_name FROM admin a LEFT JOIN admin_role r ON a.user_id = r.user_id WHERE a.username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    boolean isActive = rs.getBoolean("is_active");
                    String roleName = rs.getString("role_name");
                    Role role = roleName != null ? Role.valueOf(roleName) : Role.PUBLISHER_OF_ACADEMIC_OFFERS;

                    return new Administrator(id, firstName, lastName, username, password, email, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Administrator admin, String field, String value) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("UPDATE admin SET " + field + " = ? WHERE user_id = ?")) {
            ps.setString(1, value);
            ps.setInt(2, admin.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM admin WHERE user_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public void assignRole(int userId, String roleName) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO admin_role (user_id, role_name) VALUES (?, ?) ON DUPLICATE KEY UPDATE role_name = ?")) {
            ps.setInt(1, userId);
            ps.setString(2, roleName);
            ps.setString(3, roleName);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public void assignAdminStatus(int userId, boolean isActive) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("UPDATE admin SET is_active = ? WHERE user_id = ?")) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public Administrator findByEmail(String email) {
        try (Connection conn = dbConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT a.*, r.role_name FROM admin a LEFT JOIN admin_role r ON a.user_id = r.user_id WHERE a.email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    boolean isActive = rs.getBoolean("is_active");
                    String roleName = rs.getString("role_name");
                    Role role = roleName != null ? Role.valueOf(roleName) : Role.PUBLISHER_OF_ACADEMIC_OFFERS;

                    return new Administrator(id, firstName, lastName, username, password, email, role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
        return null;
    }
}