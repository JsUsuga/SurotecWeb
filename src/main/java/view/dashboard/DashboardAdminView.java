
package view.dashboard;

import model.domain.user.Administrator;
import service.admin.AdminServiceImpl;

import java.util.List;
import java.util.Scanner;

public class DashboardAdminView {
    private final LoginView loginView;
    private final AdminServiceImpl adminService;
    private final Scanner scanner;

    public DashboardAdminView(LoginView loginView) {
        this.loginView = loginView;
        this.adminService = new AdminServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            System.out.println("\n=== Dashboard de Administrador ===");
            System.out.println("Bienvenido, " + loginView.getLoggedInAdmin().getFirstName() + "!");
            System.out.println("1. Ver todos los administradores");
            System.out.println("2. Actualizar mi perfil");
            System.out.println("3. Asignar rol a un administrador");
            System.out.println("4. Cerrar sesión");
            System.out.print("Elige una opción: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    viewAllAdmins();
                    break;
                case 2:
                    updateProfile();
                    break;
                case 3:
                    assignRole();
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        }
    }

    private void viewAllAdmins() {
        List<Administrator> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            System.out.println("No hay administradores registrados.");
        } else {
            for (Administrator admin : admins) {
                System.out.println("ID: " + admin.getId() + ", Nombre: " + admin.getFirstName() + " " + admin.getLastName());
            }
        }
    }

    private void updateProfile() {
        Administrator admin = loginView.getLoggedInAdmin();
        System.out.print("Nuevo username: ");
        String newUsername = scanner.nextLine();
        try {
            adminService.updateAdmin(admin, "username", newUsername);
            System.out.println("Perfil actualizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el perfil: " + e.getMessage());
        }
    }

    private void assignRole() {
        System.out.print("ID del administrador: ");
        int userId = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuevo rol: ");
        String roleName = scanner.nextLine();
        try {
            adminService.assignRoleToAdmin(userId, roleName);
            System.out.println("Rol asignado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al asignar rol: " + e.getMessage());
        }
    }

    private void logout() {
        System.out.println("Cerrando sesión...");
        loginView.getLoggedInAdmin();
        System.out.println("Sesión cerrada. ¡Hasta pronto!");
    }
}