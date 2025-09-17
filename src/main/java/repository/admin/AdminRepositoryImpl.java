package repository.admin;

import model.domain.user.Administrator;
import repository.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {
    DatabaseConnection connection = new DatabaseConnection();

    @Override
    public void create(Administrator admin) {
        PreparedStatement ps = null;

        try (Connection conn = connection.connect()) {
            // Insert into user table
            String userQuery = "INSERT INTO user (first_name, last_name, username, password, email, status) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, admin.getFirstName());
            ps.setString(2, admin.getLastName());
            ps.setString(3, admin.getUsername());
            ps.setString(4, admin.getPassword());
            ps.setString(5, admin.getEmail());
            ps.setNull(6, Types.VARCHAR); // NULL status for admins
            ps.executeUpdate();

            // Get the generated user ID
            int userId;
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                    admin.setId(userId);
                } else {
                    throw new SQLException("Failed to retrieve user ID.");
                }
            }

            // Insert into admin table
            String adminQuery = "INSERT INTO admin (user_id, is_active) VALUES (?, ?)";
            ps = conn.prepareStatement(adminQuery);
            ps.setInt(1, userId);
            ps.setBoolean(2, true);
            ps.executeUpdate();

            System.out.println("Admin registered in the database: " + admin.getFirstName() + " " + admin.getLastName());
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public List<Administrator> readAll() {
        List<Administrator> admins = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection conn = connection.connect()) {
            String query = "SELECT u.*, a.is_active, r.role_name FROM user u JOIN admin a ON u.id = a.user_id LEFT JOIN user_role r ON u.id = r.user_id";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                boolean isActive = rs.getBoolean("is_active");
                String roleName = rs.getString("role_name");

                Administrator admin = new Administrator(id, firstName, lastName, username, password, email, roleName != null ? roleName : "PUBLISHER_OF_ACADEMIC_OFFERS");
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
        return admins;
    }

    @Override
    public Administrator readById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Administrator admin = null;

        try (Connection conn = connection.connect()) {
            String query = "SELECT u.*, a.is_active, r.role_name FROM user u JOIN admin a ON u.id = a.user_id LEFT JOIN user_role r ON u.id = r.user_id WHERE u.id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                boolean isActive = rs.getBoolean("is_active");
                String roleName = rs.getString("role_name");

                admin = new Administrator(userId, firstName, lastName, username, password, email, roleName != null ? roleName : "PUBLISHER_OF_ACADEMIC_OFFERS");
                System.out.println("Admin found: " + admin.getFirstName() + " " + admin.getLastName());
            } else {
                System.out.println("No admin found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
        return admin;
    }

    @Override
    public Administrator findByUserId(int userId) {
        // Assuming userId is the same as id in this context
        return readById(userId);
    }

    @Override
    public void update(Administrator admin, String field, String value) {
        PreparedStatement ps = null;

        try (Connection conn = connection.connect()) {
            String query = "UPDATE user SET " + field + " = ? WHERE id = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, value);
            ps.setInt(2, admin.getId());
            ps.executeUpdate();
            System.out.println("Admin updated in the database: " + admin.getFirstName() + " " + admin.getLastName());
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;

        try (Connection conn = connection.connect()) {
            String query = "DELETE u, a FROM user u LEFT JOIN admin a ON u.id = a.user_id WHERE u.id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Admin deleted from the database with id: " + id);
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public void assignRole(int userId, String roleName) {
        PreparedStatement ps = null;

        try (Connection conn = connection.connect()) {
            String query = "INSERT INTO user_role (user_id, role_name) VALUES (?, ?) ON DUPLICATE KEY UPDATE role_name = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, roleName);
            ps.setString(3, roleName);
            ps.executeUpdate();
            System.out.println("Role assigned to admin with user ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }

    @Override
    public void assignAdminStatus(int userId, boolean isActive) {
        PreparedStatement ps = null;

        try (Connection conn = connection.connect()) {
            String query = "INSERT INTO admin (user_id, is_active) VALUES (?, ?) ON DUPLICATE KEY UPDATE is_active = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setBoolean(2, isActive);
            ps.setBoolean(3, isActive);
            ps.executeUpdate();
            System.out.println("Admin status updated for user ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error connecting: " + e.getMessage());
        }
    }
}