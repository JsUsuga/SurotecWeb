package repository.user;

import model.domain.classification.StudentStatus;
import model.domain.user.Student;
import repository.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public void create(Student student) {
        String sql = "INSERT INTO students (id, first_name, last_name, username, password, email, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, student.getId());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getUsername());
            stmt.setString(5, student.getPassword());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getStatus().name());

            stmt.executeUpdate();
            System.out.println("Student successfully created.");

        } catch (SQLException e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
    }

    @Override
    public List<Student> readAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        StudentStatus.valueOf(rs.getString("status"))
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error reading students: " + e.getMessage());
        }

        return students;

    }

    @Override
    public Student readById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        Student student = null;

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = new Student(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            StudentStatus.valueOf(rs.getString("status"))
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error reading student by id: " + e.getMessage());
        }

        return student;
    }

    @Override
    public List<Student> readByStatus(StudentStatus status) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE status = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            StudentStatus.valueOf(rs.getString("status"))
                    );
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error reading students by status: " + e.getMessage());
        }

        return students;
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET first_name=?, last_name=?, username=?, password=?, email=?, status=? WHERE id=?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getUsername());
            stmt.setString(4, student.getPassword());
            stmt.setString(5, student.getEmail());
            stmt.setString(6, student.getStatus().name());
            stmt.setInt(7, student.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("⚠Student not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}

