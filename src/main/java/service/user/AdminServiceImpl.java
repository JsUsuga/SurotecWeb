package service.user;

import model.domain.user.Administrator;
import model.domain.classification.Role;
import repository.user.AdminRepository;
import util.Validator;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void createAdmin(Administrator admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Administrator object cannot be null.");
        }
        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (admin.getFirstName() == null || admin.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (admin.getLastName() == null || admin.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        if (admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if (!Validator.isValidEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Invalid email format for administrator.");
        }
        if (!Validator.isValidPassword(admin.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain a letter and a number.");
        }
        if (!Validator.isValidName(admin.getFirstName())) {
            throw new IllegalArgumentException("First name must contain only letters and spaces.");
        }
        if (!Validator.isValidName(admin.getLastName())) {
            throw new IllegalArgumentException("Last name must contain only letters and spaces.");
        }
        if (!Validator.isValidUsername(admin.getUsername())) {
            throw new IllegalArgumentException("Username must be alphanumeric and may include underscores.");
        }

        if (adminRepository.findByEmail(admin.getEmail()) != null) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }
        if (adminRepository.findByUsername(admin.getUsername()) != null) {
            throw new IllegalArgumentException("A user with this username already exists.");
        }

        adminRepository.create(admin);
    }

    @Override
    public List<Administrator> getAllAdmins() {
        return adminRepository.readAll();
    }

    @Override
    public Administrator getAdminById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Admin ID must be a positive integer.");
        }
        Administrator admin = adminRepository.readById(id);
        if (admin == null) {
            throw new RuntimeException("Admin not found with ID: " + id);
        }
        return admin;
    }

    @Override
    public Administrator getAdminByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }
        Administrator admin = adminRepository.findByUserId(userId);
        if (admin == null) {
            throw new RuntimeException("Admin not found with User ID: " + userId);
        }
        return admin;
    }

    @Override
    public void updateAdmin(Administrator admin, String field, String value) {
        if (admin == null || field == null || field.trim().isEmpty() || value == null) {
            throw new IllegalArgumentException("Admin, field, and value cannot be null or empty.");
        }

        if (adminRepository.readById(admin.getId()) == null) {
            throw new RuntimeException("Admin not found with ID: " + admin.getId());
        }

        String lowerField = field.toLowerCase();
        switch (lowerField) {
            case "firstname":
                if (!Validator.isValidName(value)) {
                    throw new IllegalArgumentException("First name must contain only letters and spaces.");
                }
                break;
            case "lastname":
                if (!Validator.isValidName(value)) {
                    throw new IllegalArgumentException("Last name must contain only letters and spaces.");
                }
                break;
            case "username":
                if (!Validator.isValidUsername(value)) {
                    throw new IllegalArgumentException("Username must be alphanumeric and may include underscores.");
                }
                if (adminRepository.findByUsername(value) != null && !admin.getUsername().equals(value)) {
                    throw new IllegalArgumentException("A user with this username already exists.");
                }
                break;
            case "password":
                if (!Validator.isValidPassword(value)) {
                    throw new IllegalArgumentException("Password must be at least 8 characters long and contain a letter and a number.");
                }
                break;
            case "email":
                if (!Validator.isValidEmail(value)) {
                    throw new IllegalArgumentException("Invalid email format.");
                }
                if (adminRepository.findByEmail(value) != null && !admin.getEmail().equals(value)) {
                    throw new IllegalArgumentException("A user with this email already exists.");
                }
                break;
            case "permissions":
                break;
            default:
                throw new IllegalArgumentException("Invalid field to update: " + field);
        }

        adminRepository.update(admin, field, value);
    }

    @Override
    public void deleteAdmin(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Admin ID must be a positive integer.");
        }
        Administrator admin = adminRepository.readById(id);
        if (admin == null) {
            throw new RuntimeException("Admin not found with ID: " + id + ". Deletion failed.");
        }
        adminRepository.delete(id);
    }

    @Override
    public void assignRoleToAdmin(int userId, Role roleName) {
        if (userId <= 0 || roleName == null) {
            throw new IllegalArgumentException("User ID and role name must be valid.");
        }
        if (adminRepository.findByUserId(userId) == null) {
            throw new RuntimeException("Admin not found with User ID: " + userId);
        }
        adminRepository.assignRole(userId, roleName.name());
    }

    @Override
    public void assignAdminStatus(int userId, boolean isActive) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }
        if (adminRepository.findByUserId(userId) == null) {
            throw new RuntimeException("Admin not found with User ID: " + userId);
        }
        adminRepository.assignAdminStatus(userId, isActive);
    }

    @Override
    public Administrator findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        return adminRepository.findByEmail(email);
    }

    @Override
    public Administrator findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        return adminRepository.findByUsername(username); // Delega al repositorio
    }
}