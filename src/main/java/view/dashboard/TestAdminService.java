package view.dashboard;

import model.domain.classification.Role;
import model.domain.user.Administrator;
import repository.user.AdminRepositoryImpl;
import service.user.AdminServiceImpl;

public class TestAdminService {
    public static void main(String[] args) {
        // Crear una instancia de AdminRepositoryImpl
        AdminRepositoryImpl adminRepository = new AdminRepositoryImpl();
        // Pasar el repositorio al constructor de AdminServiceImpl
        AdminServiceImpl adminService = new AdminServiceImpl(adminRepository);

        Administrator admin = new Administrator(
                0, "John", "Doe", "johndoe", "Pass12345", "john.doe@example.com", "ADMIN"
        );
        try {
            adminService.createAdmin(admin);
            System.out.println("Admin created with ID: " + admin.getId());
            adminService.assignRoleToAdmin(admin.getId(), Role.PUBLISHER_OF_ACADEMIC_OFFERS);
            System.out.println("Role assigned. Retrieved Admin: " + adminService.getAdminById(admin.getId()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}