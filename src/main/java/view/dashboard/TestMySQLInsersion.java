package view.dashboard;

import model.domain.user.Administrator;
import service.user.AdminServiceImpl;

public class TestMySQLInsersion {
    public static void main(String[] args) {
        // Crear una instancia del servicio
        AdminServiceImpl adminService = new AdminServiceImpl();

        // Crear un administrador de prueba
        Administrator admin = new Administrator(
                0, // ID se generará automáticamente
                "John",
                "Doe",
                "johndoe",
                "Pass12345",
                "john.doe@example.com",
                "ADMIN"
        );

        try {
            // Intentar crear el administrador
            adminService.createAdmin(admin);
            System.out.println("Administrator created successfully with ID: " + admin.getId());

            // Verificar que se creó correctamente obteniendo por ID
            Administrator retrievedAdmin = adminService.getAdminById(admin.getId());
            if (retrievedAdmin != null) {
                System.out.println("Retrieved Admin: " + retrievedAdmin);
            } else {
                System.out.println("Failed to retrieve the newly created admin.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Runtime Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }
}