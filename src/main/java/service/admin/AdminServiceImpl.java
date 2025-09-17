package service.admin;

import model.domain.user.Administrator;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryImpl;
import util.Validator;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl() {
        // Inyección de dependencia usando la interfaz
        this.adminRepository = new AdminRepositoryImpl();
    }

    @Override
    public void createAdmin(Administrator admin) {
        // Validaciones básicas
        if (admin == null) {
            throw new IllegalArgumentException("Administrator object cannot be null.");
        }
        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        // Validación de formato
        if (!Validator.isValidEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Invalid email format for administrator.");
        }
        if (!Validator.isValidPassword(admin.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain a letter and a number.");
        }

        // Verificar unicidad del email
        if (adminRepository.findByEmail(admin.getEmail()) != null) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        // Delegar la creación al repositorio
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

        // Validar que el admin existe
        if (adminRepository.readById(admin.getId()) == null) {
            throw new RuntimeException("Admin not found with ID: " + admin.getId());
        }

        // Validar campos permitidos para evitar inyecciones SQL
        String lowerField = field.toLowerCase();
        switch (lowerField) {
            case "firstname":
            case "lastname":
            case "username":
            case "password":
            case "email":
                // Validación adicional para email si se actualiza
                if ("email".equals(lowerField) && !Validator.isValidEmail(value)) {
                    throw new IllegalArgumentException("Invalid email format.");
                }
                // Validación adicional para password si se actualiza
                if ("password".equals(lowerField) && !Validator.isValidPassword(value)) {
                    throw new IllegalArgumentException("Password must be at least 8 characters long and contain a letter and a number.");
                }
                adminRepository.update(admin, field, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid field to update: " + field);
        }
    }

    @Override
    public void deleteAdmin(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Admin ID must be a positive integer.");
        }
        // Verificar que el admin existe antes de borrar
        Administrator admin = adminRepository.readById(id);
        if (admin == null) {
            throw new RuntimeException("Admin not found with ID: " + id + ". Deletion failed.");
        }
        adminRepository.delete(id);
    }

    @Override
    public void assignRoleToAdmin(int userId, String roleName) {
        if (userId <= 0 || roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID and role name must be valid.");
        }
        // Verificar que el admin existe
        if (adminRepository.findByUserId(userId) == null) {
            throw new RuntimeException("Admin not found with User ID: " + userId);
        }
        adminRepository.assignRole(userId, roleName);
    }

    @Override
    public void assignAdminStatus(int userId, boolean isActive) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive integer.");
        }
        // Verificar que el admin existe
        if (adminRepository.findByUserId(userId) == null) {
            throw new RuntimeException("Admin not found with User ID: " + userId);
        }
        adminRepository.assignAdminStatus(userId, isActive);
    }
}