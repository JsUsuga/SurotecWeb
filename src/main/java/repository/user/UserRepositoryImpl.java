package repository.user;

import repository.config.DatabaseConnection;
import model.domain.user.Administrator;
import model.domain.user.Student;
import model.domain.user.User;
import model.domain.classification.StudentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private DatabaseConnection dbConnection;

    public UserRepositoryImpl() {
        this.dbConnection = new DatabaseConnection();
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password, email, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getEmail());
            if (user instanceof Student) {
                pstmt.setString(6, ((Student) user).getStatus().name());
            } else {
                pstmt.setNull(6, Types.VARCHAR); // No status for non-students
            }
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            System.out.println("Usuario registrado en la base de datos: " + user.getFirstName() + " " + user.getLastName());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = rs.getString("status") != null
                        ? new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("email"),
                        StudentStatus.valueOf(rs.getString("status")))
                        : new Administrator(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("email"), "manage");
                users.add(user);
            }
            System.out.println("Lista de usuarios recuperada.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return users;
    }

    @Override
    public User readById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status") != null
                        ? new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("email"),
                        StudentStatus.valueOf(rs.getString("status")))
                        : new Administrator(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("email"), "manage");
            }
            System.out.println("Usuario con ID " + id + " no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status") != null
                        ? new Student(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("email"),
                        StudentStatus.valueOf(rs.getString("status")))
                        : new Administrator(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("email"), "manage");
            }
            System.out.println("Usuario con email " + email + " no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void assignRole(int userId, String roleName) {
        String sql = "INSERT INTO user_roles (user_id, role_name) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, roleName);
            pstmt.executeUpdate();
            System.out.println("Rol asignado al usuario ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public void updateUser(User user, String field, String value) {
        String sql = "UPDATE users SET " + field + " = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
            System.out.println("Usuario actualizado: " + user.getFirstName() + " " + user.getLastName());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado con ID: " + id);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}